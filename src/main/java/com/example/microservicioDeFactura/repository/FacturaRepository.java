package com.example.microservicioDeFactura.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.example.microservicioDeFactura.model.factura;

import java.util.List;

@Repository
public interface FacturaRepository extends CrudRepository<factura, Integer> {

    @Query("SELECT f FROM factura f")
    List<factura> listarFacturas();

    @Query("SELECT f FROM factura f WHERE f.rutCliente = :rutCliente")
    List<factura> buscarPorRut(int rutCliente);

    @Query("select f from factura f where f.id = :idBuscar" )
    List<factura> buscarPorId(int idBuscar);

    @Modifying
    @Transactional
    @Query("DELETE FROM factura f WHERE f.id = :id")
    void eliminarPorId(int id);


}
