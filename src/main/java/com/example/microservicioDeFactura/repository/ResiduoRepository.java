package com.example.microservicioDeFactura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.microservicioDeFactura.model.Residuo;

import jakarta.transaction.Transactional;

@Repository
public interface ResiduoRepository extends JpaRepository<Residuo, Integer> {

    @Query("SELECT r FROM Residuo r")
    List<Residuo> listarFacturas();

    @Query("SELECT r FROM Residuo r WHERE r.id = :idBuscar")
    List<Residuo> buscarPorId(Integer idBuscar);

    @Modifying
    @Transactional
    @Query("DELETE FROM Residuo r WHERE r.id = :id")
    void eliminarPorId(@Param("id") Integer id);

    @Query("UPDATE Residuo r SET r.tipo = :tipo, r.peso = :peso, r.peligrosidad = :peligrosidad, r.empresaEmisora = :empresaEmisora, r.volumen = :volumen, r.clasificacion = :clasificacion WHERE r.id = :id")
    void actualizarResiduo(@Param("id") Integer id, 
                           @Param("tipo") String tipo, 
                           @Param("peso") Integer peso, 
                           @Param("peligrosidad") String peligrosidad, 
                           @Param("empresaEmisora") String empresaEmisora, 
                           @Param("volumen") String volumen, 
                           @Param("clasificacion") String clasificacion);                      
}

