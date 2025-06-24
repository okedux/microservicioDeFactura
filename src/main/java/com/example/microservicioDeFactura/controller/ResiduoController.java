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

/**
 * Controlador REST para operaciones CRUD sobre Residuos.
 */
@RestController
@RequestMapping("/api/residuos/v1")
public class ResiduoController {

    @Autowired
    private ResiduoService residuoService;

    /**
     * Lista todos los residuos.
     * @return Lista de residuos o 204 si está vacía.
     */
    @GetMapping("/listarResiduos")
    public ResponseEntity<List<Residuo>> listar() {
        List<Residuo> residuos = residuoService.findAll();
        if (residuos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(residuos);
    }

    /**
     * Busca un residuo por su ID.
     * @param id ID del residuo.
     * @return Residuo encontrado o 204 si no existe.
     */
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<Residuo> buscarPorId(@PathVariable Integer id) {
        Optional<Residuo> residuo = residuoService.findById(id);
        return residuo.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.noContent().build());
    }

    /**
     * Elimina un residuo por su ID.
     * @param id ID del residuo a eliminar.
     * @return 204 si se elimina, 404 si no existe.
     */
    @DeleteMapping("/eliminarPorId/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Integer id) {
        Optional<Residuo> residuo = residuoService.findById(id);
        if (residuo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        residuoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Guarda un nuevo residuo.
     * @param nuevoResiduo Residuo a guardar.
     * @return 200 OK si se guarda, 400 si el cuerpo es inválido.
     */
    @PostMapping("/guardarResiduos")
    public ResponseEntity<Void> guardar(@RequestBody Residuo nuevoResiduo) {
        if (nuevoResiduo == null) {
            return ResponseEntity.badRequest().build();
        }
        residuoService.guardar(nuevoResiduo);
        return ResponseEntity.ok().build();
    }

    /**
     * Actualiza un residuo existente.
     * @param id ID del residuo a actualizar.
     * @param nuevoResiduo Datos actualizados del residuo.
     * @return 200 OK si se actualiza, 404 si no existe.
     */
    @PatchMapping("/actualizarResiduos/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Integer id, @RequestBody Residuo nuevoResiduo) {
        Optional<Residuo> existente = residuoService.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        residuoService.actualizar(id, nuevoResiduo);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint de salud para verificar que el servicio está activo.
     * @return Mensaje de estado.
     */
    @GetMapping
    public String health() {
        return "Service is running!";
    }
}
