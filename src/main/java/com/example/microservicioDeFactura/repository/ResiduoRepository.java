package com.example.microservicioDeFactura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.microservicioDeFactura.model.Residuo;

import jakarta.transaction.Transactional;

/**
 * Repositorio para operaciones CRUD y consultas personalizadas sobre la entidad Residuo.
 */
@Repository
public interface ResiduoRepository extends JpaRepository<Residuo, Integer> {

    /**
     * Obtiene todos los residuos.
     * @return Lista de residuos.
     */
    @Query("SELECT r FROM Residuo r")
    List<Residuo> listarFacturas();

    /**
     * Busca residuos por su ID.
     * @param idBuscar ID del residuo.
     * @return Lista de residuos con el ID especificado.
     */
    @Query("SELECT r FROM Residuo r WHERE r.id = :idBuscar")
    List<Residuo> buscarPorId(Integer idBuscar);

    /**
     * Elimina un residuo por su ID.
     * @param id ID del residuo a eliminar.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Residuo r WHERE r.id = :id")
    void eliminarPorId(@Param("id") Integer id);

    /**
     * Actualiza los datos de un residuo existente.
     * @param id ID del residuo.
     * @param tipo Nuevo tipo.
     * @param peso Nuevo peso.
     * @param peligrosidad Nueva peligrosidad.
     * @param empresaEmisora Nueva empresa emisora.
     * @param volumen Nuevo volumen.
     * @param clasificacion Nueva clasificación.
     */
    @Query("UPDATE Residuo r SET r.tipo = :tipo, r.peso = :peso, r.peligrosidad = :peligrosidad, r.empresaEmisora = :empresaEmisora, r.volumen = :volumen, r.clasificacion = :clasificacion WHERE r.id = :id")
    void actualizarResiduo(@Param("id") Integer id, 
                           @Param("tipo") String tipo, 
                           @Param("peso") Integer peso, 
                           @Param("peligrosidad") String peligrosidad, 
                           @Param("empresaEmisora") String empresaEmisora, 
                           @Param("volumen") String volumen, 
                           @Param("clasificacion") String clasificacion);                      
}

