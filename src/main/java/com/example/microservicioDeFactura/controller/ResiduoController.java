package com.example.microservicioDeFactura.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservicioDeFactura.model.Residuo;
import com.example.microservicioDeFactura.service.ResiduoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/residuos/v1")
public class ResiduoController {

    @Autowired
    private ResiduoService residuoService;

    @GetMapping("/listarResiduos")
    public ResponseEntity<List<Residuo>> listar() {
        List<Residuo> residuos = residuoService.findAll();
        if (residuos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(residuos);
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<Residuo> buscarPorId(@PathVariable Long id) {
        Optional<Residuo> residuo = residuoService.findById(id);
        return residuo.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @DeleteMapping("/eliminarPorId/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        Optional<Residuo> residuo = residuoService.findById(id);
        if (residuo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        residuoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/guardarResiduos")
    public ResponseEntity<Void> guardar(@RequestBody Residuo nuevoResiduo) {
        if (nuevoResiduo == null) {
            return ResponseEntity.badRequest().build();
        }
        residuoService.guardar(nuevoResiduo);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/actualizarResiduos/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Long id, @RequestBody Residuo nuevoResiduo) {
        Optional<Residuo> existente = residuoService.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        residuoService.actualizar(id, nuevoResiduo);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public String health() {
        return "Service is running!";
    }
}
