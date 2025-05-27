package com.example.microservicioDeFactura.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.microservicioDeFactura.model.Factura;
import com.example.microservicioDeFactura.repository.FacturaRepository;

@Service
public class facturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    public List<Factura> listarFacturas() {
        return (List<Factura>) facturaRepository.findAll();
    }

    public List<Factura> buscarPorRutEmpresa(String rutEmpresa) {
        return facturaRepository.buscarPorRutEmpresa(rutEmpresa);
    }

    public List<Factura> buscarPorId(int idBuscar) {
        return facturaRepository.buscarPorId(idBuscar);
    }

    public void eliminarPorId(int idEliminar) {
        facturaRepository.eliminarPorId(idEliminar);
    }

    public void guardarFactura(Factura nuevaFactura) {
        facturaRepository.save(nuevaFactura);
    }
}