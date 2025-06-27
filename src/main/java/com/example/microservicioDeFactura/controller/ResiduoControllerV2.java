package com.example.microservicioDeFactura.controller;

import com.example.microservicioDeFactura.assemblers.ResiduoModelAssembler;
import com.example.microservicioDeFactura.model.Residuo;
import com.example.microservicioDeFactura.service.ResiduoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Controlador REST HATEOAS para operaciones CRUD sobre Residuos (v2).
 */
@RestController
@RequestMapping("/api/residuos/v2")
public class ResiduoControllerV2 {

    @Autowired
    private ResiduoService residuoService;

    @Autowired
    private ResiduoModelAssembler residuoModelAssembler;

    /**
     * Lista todos los residuos con enlaces HATEOAS.
     */
    @GetMapping
    public CollectionModel<EntityModel<Residuo>> listarResiduos() {
        List<EntityModel<Residuo>> residuos = residuoService.findAll().stream()
                .map(residuoModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(residuos,
                linkTo(methodOn(ResiduoControllerV2.class).listarResiduos()).withSelfRel());
    }

    /**
     * Busca un residuo por su ID con enlaces HATEOAS.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Residuo>> buscarPorId(@PathVariable Integer id) {
        Optional<Residuo> residuoOpt = residuoService.findById(id);
        return residuoOpt
                .map(residuoModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Guarda un nuevo residuo y retorna el recurso con enlaces HATEOAS.
     */
    @PostMapping
    public ResponseEntity<EntityModel<Residuo>> guardar(@RequestBody Residuo nuevoResiduo) {
        if (nuevoResiduo == null) {
            return ResponseEntity.badRequest().build();
        }
        residuoService.guardar(nuevoResiduo);
        EntityModel<Residuo> residuoModel = residuoModelAssembler.toModel(nuevoResiduo);
        return ResponseEntity
                .created(residuoModel.getRequiredLink("self").toUri())
                .body(residuoModel);
    }

    /**
     * Actualiza un residuo existente y retorna el recurso actualizado con enlaces HATEOAS.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<Residuo>> actualizar(@PathVariable Integer id, @RequestBody Residuo nuevoResiduo) {
        Optional<Residuo> existente = residuoService.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        residuoService.actualizar(id, nuevoResiduo);
        EntityModel<Residuo> residuoModel = residuoModelAssembler.toModel(nuevoResiduo);
        return ResponseEntity.ok(residuoModel);
    }

    /**
     * Elimina un residuo por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Integer id) {
        Optional<Residuo> existente = residuoService.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        residuoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
