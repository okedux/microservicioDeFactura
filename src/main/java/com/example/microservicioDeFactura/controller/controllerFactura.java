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
import com.example.microservicioDeFactura.model.factura;
import com.example.microservicioDeFactura.repository.FacturaRepository;
import com.example.microservicioDeFactura.service.facturaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/factura/v1")
public class controllerFactura {

    private final FacturaRepository facturaRepository;

    @Autowired
    private facturaService facturaService;

    controllerFactura(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<factura>> listarFacturas() {
        List<factura> facturas = facturaService.listarFacturas();
        if(facturas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }
    @GetMapping("/buscarPorRut/{rutCliente}")
    public List<factura> buscarPorRut(@PathVariable int rutCliente) {
         return facturaService.buscarPorRut(rutCliente);
    }
    @GetMapping("/buscarPorId/{idBuscar}")
    public List<factura> buscarPorId(@PathVariable int idBuscar) {
        return facturaService.buscarPorId(idBuscar);
    }
    @DeleteMapping("/eliminarPorId/{idEliminar}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable int idEliminar) {
        facturaService.eliminarPorId(idEliminar);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/guardarFactura")
    public ResponseEntity<Void> guardarFactura(@RequestBody factura nuevaFactura) {
        System.out.println("Factura recibida: " + nuevaFactura);
        facturaService.guardarFactura(nuevaFactura);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/actualizarFactura/{id}")
    public ResponseEntity<Void> actualizarFactura(@PathVariable int id, @RequestBody factura facturaActualizada) {
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
