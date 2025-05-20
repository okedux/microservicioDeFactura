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
        return facturaRepository.buscarPorRut(rutCliente);
    }

    public List<factura> buscarPorId(int idBuscar) {
        return facturaRepository.buscarPorId(idBuscar);
    }

    public void eliminarPorId(int idEliminar) {
        facturaRepository.eliminarPorId(idEliminar);
    }

    public void guardarFactura(factura nuevaFactura) {
        facturaRepository.save(nuevaFactura);
    }
}