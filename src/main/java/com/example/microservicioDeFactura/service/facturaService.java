package com.example.microservicioDeFactura.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.microservicioDeFactura.model.factura;
import com.example.microservicioDeFactura.repository.FacturaRepository;

@Service
public class facturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    public List<factura> listarFacturas() {
        return (List<factura>) facturaRepository.findAll();
    }

    public List<factura> buscarPorRut(int rutCliente) {
        // Implementación de búsqueda por RUT
        return null;
    }

    public List<factura> buscarPorId(int idBuscar) {
        // Implementación de búsqueda por ID
        return null;
    }

    public void eliminarPorId(int idEliminar) {
        facturaRepository.eliminarPorId(idEliminar);
    }
}