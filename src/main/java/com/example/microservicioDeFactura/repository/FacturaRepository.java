package com.example.microservicioDeFactura.repository;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.example.microservicioDeFactura.model.factura;

@Repository
public interface FacturaRepository extends JpaRepository<factura, Integer>{

    @Query("SELECT f FROM factura f")
    List<factura> listarFacturas();

    @Query("select f from factura f where f.rutCliente" )
    List<factura> buscarPorRut(@Param("rutCliente") String rutCliente);

    @Query("select f from factura f where f.id" )
    List<factura> buscarPorId(@Param("id") String idBuscar);

    @Query("delete f from factura f where f.id" )
    List<factura> eliminarPorId(@Param("id") String idEliminar);


    @Query("INSERT INTO factura (rutCliente, dvRut, nombreReceptor, valor, cantidadDesechos, tipoDeResiduos, fechaEmision) VALUES (:rutCliente, :dvRut, :nombreReceptor, :valor, :cantidadDesechos, :tipoDeResiduos, :fechaEmision)")
    List<factura> insertarFactura(factura nuevaFactura, @Param("rutCliente") int rutCliente, @Param("dvRut") String dvRut, @Param("nombreReceptor") String nombreReceptor, @Param("valor") int valor, @Param("cantidadDesechos") float cantidadDesechos, @Param("tipoDeResiduos") String tipoDeResiduos, @Param("fechaEmision") Date fechaEmision);

}
