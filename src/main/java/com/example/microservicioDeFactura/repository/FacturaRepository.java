package com.example.microservicioDeFactura.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.example.microservicioDeFactura.model.factura;

@Repository
public interface FacturaRepository extends JpaRepository<factura, Long>{

    @Query("SELECT f FROM factura f")
    List<factura> listarFacturas();

    @Query("select f from factura f where f.rutCliente" )
    List<factura> buscarPorRut(@Param("rutCliente") String rutCliente);

    @Query("select f from factura f where f.id" )
    List<factura> buscarPorId(@Param("id") String idBuscar);

    @Query("delete f from factura f where f.id" )
    List<factura> eliminarPorId(@Param("id") String idEliminar);

    @Query("INSERT INTO factura (rutCliente, dvRut, nombreReceptor, valor, cantidadDesechos, tipoDeResiduos, fechaEmision) VALUES       (:rutCliente, :dvRut, :nombreReceptor, :valor, :cantidadDesechos, :tipoDeResiduos, :fechaEmision)")
    
    void insertarFactura(@Param("rutCliente") String rutCliente, 
                         @Param("dvRut") String dvRut, 
                         @Param("valor") String valor,
                         @Param("cantidadDesechos") String cantidadDesechos,
                         @Param("tipoDeResiduos") String tipoDeResiduos,
                         @Param("fechaEmision") String fechaEmision);
}
