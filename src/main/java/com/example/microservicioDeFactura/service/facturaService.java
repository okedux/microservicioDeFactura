package com.example.microservicioDeFactura.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.microservicioDeFactura.model.Factura;
import com.example.microservicioDeFactura.repository.FacturaRepository;

/**
 * Servicio para la gestión de facturas.
 * Proporciona métodos para operaciones CRUD y consultas personalizadas sobre la entidad Factura.
 */
@Service
public class facturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    /**
     * Lista todas las facturas.
     * @return Lista de facturas.
     */
    public List<Factura> listarFacturas() {
        return (List<Factura>) facturaRepository.findAll();
    }

    /**
     * Busca facturas por el RUT de la empresa.
     * @param rutEmpresa RUT de la empresa.
     * @return Lista de facturas asociadas al RUT.
     */
    public List<Factura> buscarPorRutEmpresa(String rutEmpresa) {
        return facturaRepository.buscarPorRutEmpresa(rutEmpresa);
    }

    /**
     * Busca una factura por su ID.
     * @param idBuscar ID de la factura.
     * @return Factura con el ID especificado o vacío si no existe.
     */
    public Optional<Factura> buscarPorId(int idBuscar) {
        return facturaRepository.buscarPorId(idBuscar);
    }

    /**
     * Elimina una factura por su ID.
     * @param idEliminar ID de la factura a eliminar.
     */
    public void eliminarPorId(int idEliminar) {
        facturaRepository.eliminarPorId(idEliminar);
    }

    /**
     * Guarda una nueva factura o actualiza una existente.
     * @param nuevaFactura Factura a guardar.
     * @return Factura guardada.
     */
    public Factura guardarFactura(Factura nuevaFactura) {
        return facturaRepository.save(nuevaFactura);
    }
}