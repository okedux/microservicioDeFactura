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

/**
 * Controlador REST para operaciones CRUD sobre Facturas (v1).
 */
@RestController
@RequestMapping("/api/factura/v1")
public class controllerFactura {

    @Autowired
    private facturaService facturaService;

    // Constructor (puedes eliminarlo si no lo usas)
    controllerFactura(FacturaRepository facturaRepository) {
    }

    /**
     * Lista todas las facturas.
     * @return Lista de facturas o 204 si está vacía.
     */
    @GetMapping("/listar")
    public ResponseEntity<List<Factura>> listarFacturas() {
        List<Factura> facturas = facturaService.listarFacturas();
        if(facturas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }

    /**
     * Busca facturas por RUT de empresa.
     * @param rutEmpresa RUT de la empresa emisora.
     * @return Lista de facturas o 204 si no hay resultados.
     */
    @GetMapping("/buscarPorRut/{rutEmpresa}")
    public ResponseEntity<List<Factura>> buscarPorRutEmpresa(@PathVariable String rutEmpresa) {
        List<Factura> facturas = facturaService.buscarPorRutEmpresa(rutEmpresa);
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }

    /**
         * Busca una factura por ID.
         * @param id ID de la factura.
         * @return Factura encontrada o 204 si no hay resultados.
         */
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<Factura> buscarPorId(@PathVariable int id) {    
        return facturaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    /**
     * Elimina una factura por su ID.
     * @param idEliminar ID de la factura a eliminar.
     * @return 204 No Content.
     */
    @DeleteMapping("/eliminarPorId/{idEliminar}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable int idEliminar) {
        facturaService.eliminarPorId(idEliminar);
        return ResponseEntity.noContent().build();
    }

    /**
     * Guarda una nueva factura.
     * @param nuevaFactura Factura a guardar.
     * @return 200 OK si se guarda correctamente.
     */
    @PostMapping("/guardarFactura")
    public ResponseEntity<Void> guardarFactura(@RequestBody Factura nuevaFactura) {
        System.out.println("Factura recibida: " + nuevaFactura);
        facturaService.guardarFactura(nuevaFactura);
        return ResponseEntity.ok().build();
    }

    /**
     * Actualiza una factura existente.
     * @param id ID de la factura a actualizar.
     * @param facturaActualizada Datos actualizados de la factura.
     * @return 200 OK si se actualiza, 404 si no existe.
     */
    @PatchMapping("/actualizarFactura/{id}")
    public ResponseEntity<Void> actualizarFactura(@PathVariable int id, @RequestBody Factura facturaActualizada) {
        if (facturaService.buscarPorId(facturaActualizada.getId())
                .stream()
                .anyMatch(f -> f.getId() == id)) {
            facturaService.guardarFactura(facturaActualizada);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
