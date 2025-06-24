package com.example.microservicioDeFactura.service;

import com.example.microservicioDeFactura.model.Cliente;
import com.example.microservicioDeFactura.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de clientes.
 * Proporciona métodos para operaciones CRUD sobre la entidad Cliente.
 */
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Lista todos los clientes.
     * @return Lista de clientes.
     */
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    /**
     * Busca un cliente por su ID.
     * @param id ID del cliente.
     * @return Cliente encontrado o vacío si no existe.
     */
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    /**
     * Guarda un nuevo cliente o actualiza uno existente.
     * @param cliente Cliente a guardar.
     * @return Cliente guardado.
     */
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Elimina un cliente por su ID.
     * @param id ID del cliente a eliminar.
     */
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }

    /**
     * Busca un cliente por su RUT de empresa.
     * @param rutEmpresa RUT de la empresa.
     * @return Cliente encontrado o vacío si no existe.
     */
    public Optional<Cliente> buscarPorRut(String rutEmpresa) {
        return Optional.ofNullable(clienteRepository.findByRutEmpresa(rutEmpresa));
    }
}