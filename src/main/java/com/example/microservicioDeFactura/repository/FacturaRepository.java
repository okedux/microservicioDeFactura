package com.example.microservicioDeFactura.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.example.microservicioDeFactura.model.Factura;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones CRUD y consultas personalizadas sobre la entidad Factura.
 */
@Repository
public interface FacturaRepository extends CrudRepository<Factura, Integer> {

    /**
     * Obtiene todas las facturas.
     * @return Lista de todas las facturas.
     */
    @Query("SELECT f FROM Factura f")
    List<Factura> listarFacturas();

    /**
     * Busca facturas por el RUT de la empresa del cliente.
     * @param rutEmpresa RUT de la empresa.
     * @return Lista de facturas asociadas al RUT.
     */
    @Query("SELECT f FROM Factura f INNER JOIN f.cliente c WHERE c.rutEmpresa = :rutEmpresa")
    List<Factura> buscarPorRutEmpresa(String rutEmpresa);

    /**
     * Busca una factura por su ID.
     * @param idBuscar ID de la factura.
     * @return Factura con el ID especificado o vacío si no existe.
     */
    @Query("SELECT f FROM Factura f WHERE f.id = :idBuscar")
    Optional<Factura> buscarPorId(int idBuscar);

    /**
     * Elimina una factura por su ID.
     * @param id ID de la factura a eliminar.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Factura f WHERE f.id = :id")
    void eliminarPorId(Integer id);

}
