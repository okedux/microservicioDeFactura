package com.example.microservicioDeFactura.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicioDeFactura.model.Residuo;
import com.example.microservicioDeFactura.repository.ResiduoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResiduoService {

    @Autowired
    private ResiduoRepository residuoRepository;
    
    public List<Residuo> findAll() {
        return residuoRepository.findAll();
    }

    public void eliminarPorId(Long id) {
        residuoRepository.deleteById(id);
    }

    public void guardar(Residuo nuevoResiduo) {
        residuoRepository.save(nuevoResiduo);
    }

    public Optional<Residuo> findById(Long id) {
        return residuoRepository.findById(id);
    }

    public void actualizar(Long id, Residuo datosActualizados) {
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
