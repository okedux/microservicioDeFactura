package com.example.microservicioDeFactura.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.example.microservicioDeFactura.model.Factura;

import java.util.List;

@Repository
public interface FacturaRepository extends CrudRepository<Factura, Integer> {

    @Query("SELECT f FROM Factura f")
    List<Factura> listarFacturas();

    @Query("SELECT f FROM Factura f INNER JOIN f.cliente c WHERE c.rutEmpresa = :rutEmpresa")
    List<Factura> buscarPorRutEmpresa(String rutEmpresa);

    @Query("SELECT f FROM Factura f WHERE f.id = :idBuscar")
    List<Factura> buscarPorId(int idBuscar);

    @Modifying
    @Transactional
    @Query("DELETE FROM Factura f WHERE f.id = :id")
    void eliminarPorId(int id);

}
