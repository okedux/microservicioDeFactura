package com.example.microservicioDeFactura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.microservicioDeFactura.model.factura;
import com.example.microservicioDeFactura.service.facturaService;

@RestController
@RequestMapping("/api/factura/v1")
public class controllerFactura {

    @Autowired
    private factura nuevaFactura;

    @Autowired
    private facturaService facturaService;
    @GetMapping("/listar")
    public List<factura> listarFacturas() {
        return facturaService.listarFacturas();
    }
    @GetMapping("/buscarPorRut/{rutCliente}")
    public List<factura> buscarPorRut(@PathVariable String rutCliente) {
         return facturaService.buscarPorRut(rutCliente);
    }
    @GetMapping("/buscarPorId/{idBuscar}")
    public List<factura> buscarPorId(@PathVariable String idBuscar) {
        return facturaService.buscarPorId(idBuscar);
    }
    @GetMapping("/eliminarPorId/{idEliminar}")
    public List<factura> eliminarPorId(@PathVariable String idEliminar) {
        return facturaService.eliminarPorId(idEliminar);
    }
}
