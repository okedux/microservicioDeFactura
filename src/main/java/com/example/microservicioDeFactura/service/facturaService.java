package com.example.microservicioDeFactura.service;

import java.util.Date;
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
        return facturaRepository.listarFacturas();
    }

    public List<factura> buscarPorRut(String rutCliente) {
        return facturaRepository.buscarPorRut(rutCliente);
    }

    public List<factura> buscarPorId(String idBuscar) {
        return facturaRepository.buscarPorId(idBuscar);
    }

    public List<factura> eliminarPorId(String idEliminar) {
        return facturaRepository.eliminarPorId(idEliminar);
    }

    public void insertarFactura(factura nuevaFactura) {
        facturaRepository.insertar(nuevaFactura);
    }
}
