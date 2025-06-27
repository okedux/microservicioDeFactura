package com.example.microservicioDeFactura.controller;

import com.example.microservicioDeFactura.assemblers.FacturaModelAssembler;
import com.example.microservicioDeFactura.model.Factura;
import com.example.microservicioDeFactura.service.facturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

// Controlador REST HATEOAS para operaciones CRUD sobre Facturas (v2).
@RestController
@RequestMapping("/api/factura/v2")
public class FacturaControllerV2 {

    @Autowired
    private facturaService facturaService;

    @Autowired
    private FacturaModelAssembler facturaModelAssembler;

    // Lista todas las facturas con enlaces HATEOAS.
    @GetMapping
    public CollectionModel<EntityModel<Factura>> listarFacturas() {
        List<EntityModel<Factura>> facturas = facturaService.listarFacturas().stream()
                .map(facturaModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(facturas,
                linkTo(methodOn(FacturaControllerV2.class).listarFacturas()).withSelfRel());
    }

    // Busca una factura por su ID con enlaces HATEOAS.
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Factura>> buscarPorId(@PathVariable Integer id) {
        Optional<Factura> facturaOpt = facturaService.buscarPorId(id);
        return facturaOpt
                .map(facturaModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crea una nueva factura y retorna el recurso con enlaces HATEOAS.
     
    @PostMapping
    public ResponseEntity<EntityModel<Factura>> crearFactura(@RequestBody Factura factura) {
        Factura guardada = facturaService.guardarFactura(factura);
        EntityModel<Factura> facturaModel = facturaModelAssembler.toModel(guardada);
        return ResponseEntity
                .created(facturaModel.getRequiredLink("self").toUri())
                .body(facturaModel);
    }

    // Actualiza una factura existente y retorna el recurso actualizado con enlaces HATEOAS.

    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<Factura>> actualizarFactura(@PathVariable Integer id, @RequestBody Factura factura) {
        Optional<Factura> existente = facturaService.buscarPorId(id);
        if (existente.isPresent()) {
            factura.setId(id);
            Factura actualizada = facturaService.guardarFactura(factura);
            EntityModel<Factura> facturaModel = facturaModelAssembler.toModel(actualizada);
            return ResponseEntity.ok(facturaModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Elimina una factura por su ID.

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Integer id) {
        Optional<Factura> existente = facturaService.buscarPorId(id);
        if (existente.isPresent()) {
            facturaService.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
