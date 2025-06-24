package com.example.microservicioDeFactura.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicioDeFactura.model.Residuo;
import com.example.microservicioDeFactura.repository.ResiduoRepository;

import jakarta.transaction.Transactional;

/**
 * Servicio para la gestión de residuos.
 * Proporciona métodos para operaciones CRUD sobre la entidad Residuo.
 */
@Service
@Transactional
public class ResiduoService {

    @Autowired
    private ResiduoRepository residuoRepository;

    /**
     * Lista todos los residuos.
     * @return Lista de residuos.
     */
    public List<Residuo> findAll() {
        return residuoRepository.findAll();
    }

    /**
     * Elimina un residuo por su ID.
     * @param id ID del residuo a eliminar.
     */
    public void eliminarPorId(Integer id) {
        residuoRepository.deleteById(id);
    }

    /**
     * Guarda un nuevo residuo.
     * @param nuevoResiduo Residuo a guardar.
     */
    public void guardar(Residuo nuevoResiduo) {
        residuoRepository.save(nuevoResiduo);
    }

    /**
     * Busca un residuo por su ID.
     * @param id ID del residuo.
     * @return Residuo encontrado o vacío si no existe.
     */
    public Optional<Residuo> findById(Integer id) {
        return residuoRepository.findById(id);
    }

    /**
     * Actualiza los datos de un residuo existente.
     * @param id ID del residuo a actualizar.
     * @param datosActualizados Datos nuevos del residuo.
     */
    public void actualizar(Integer id, Residuo datosActualizados) {
        Optional<Residuo> existenteOpt = residuoRepository.findById(id);
        if (existenteOpt.isPresent()) {
            Residuo existente = existenteOpt.get();
            existente.setTipo(datosActualizados.getTipo());
            existente.setPeso(datosActualizados.getPeso());
            existente.setPeligrosidad(datosActualizados.getPeligrosidad());
            existente.setEmpresaEmisora(datosActualizados.getEmpresaEmisora());
            existente.setVolumen(datosActualizados.getVolumen());
            existente.setClasificacion(datosActualizados.getClasificacion());
            existente.setNombre(datosActualizados.getNombre());
            residuoRepository.save(existente);
        }
    }
}
