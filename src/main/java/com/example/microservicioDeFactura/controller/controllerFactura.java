package com.example.microservicioDeFactura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.microservicioDeFactura.model.Factura;
import com.example.microservicioDeFactura.repository.FacturaRepository;
import com.example.microservicioDeFactura.service.facturaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/factura/v1")
public class controllerFactura {

    @Autowired
    private facturaService facturaService;

    controllerFactura(FacturaRepository facturaRepository) {
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Factura>> listarFacturas() {
        List<Factura> facturas = facturaService.listarFacturas();
        if(facturas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }
    @GetMapping("/buscarPorRut/{rutEmpresa}")
    public ResponseEntity<List<Factura>> buscarPorRutEmpresa(@PathVariable String rutEmpresa) {
        List<Factura> facturas = facturaService.buscarPorRutEmpresa(rutEmpresa);
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<List<Factura>> buscarPorId(@PathVariable int id) {
        List<Factura> facturas = facturaService.buscarPorId(id);
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }
    @DeleteMapping("/eliminarPorId/{idEliminar}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable int idEliminar) {
        facturaService.eliminarPorId(idEliminar);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/guardarFactura")
    public ResponseEntity<Void> guardarFactura(@RequestBody Factura nuevaFactura) {
        System.out.println("Factura recibida: " + nuevaFactura);
        facturaService.guardarFactura(nuevaFactura);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/actualizarFactura/{id}")
    public ResponseEntity<Void> actualizarFactura(@PathVariable int id, @RequestBody Factura facturaActualizada) {
        if (facturaService.buscarPorId(facturaActualizada.getId())
                .stream()
                .anyMatch(f -> f.getId() == id)) {
                    facturaService.guardarFactura(facturaActualizada);
                    return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
