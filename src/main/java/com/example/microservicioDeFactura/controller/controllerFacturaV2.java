package com.example.microservicioDeFactura.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.microservicioDeFactura.assemblers.FacturaModelAssembler;
import com.example.microservicioDeFactura.model.Factura;
import com.example.microservicioDeFactura.service.facturaService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Controlador REST HATEOAS para operaciones CRUD sobre Facturas (v2).
 */
@RestController
@RequestMapping("/api/factura/v2")
public class controllerFacturaV2 {
    @Autowired
    private facturaService facturaService;

    @Autowired
    private FacturaModelAssembler facturaModelAssembler;

    @GetMapping
    public CollectionModel<EntityModel<Factura>> listarFacturas() {
        List<EntityModel<Factura>> facturas = facturaService.listarFacturas()
                .stream()
                .map(facturaModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(facturas,
                linkTo(methodOn(controllerFacturaV2.class).listarFacturas()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Factura>> buscarPorId(@PathVariable Integer id) {
        // Cambia buscarPorId para que devuelva Optional<Factura>
        return facturaService.buscarPorId(id)
                .map(facturaModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Factura>> crearFactura(@RequestBody Factura factura) {
        Factura guardada = facturaService.guardarFactura(factura);
        EntityModel<Factura> facturaModel = facturaModelAssembler.toModel(guardada);
        return ResponseEntity
                .created(facturaModel.getRequiredLink("self").toUri())
                .body(facturaModel);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<Factura>> actualizarFactura(@PathVariable Integer id, @RequestBody Factura factura) {
        return facturaService.buscarPorId(id)
                .map(existing -> {
                    factura.setId(id);
                    Factura actualizada = facturaService.guardarFactura(factura);
                    return ResponseEntity.ok(facturaModelAssembler.toModel(actualizada));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Integer id) {
        return facturaService.buscarPorId(id)
                .map(f -> {
                    facturaService.eliminarPorId(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
