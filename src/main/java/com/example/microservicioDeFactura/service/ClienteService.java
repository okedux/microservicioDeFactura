package com.example.microservicioDeFactura.service;

import com.example.microservicioDeFactura.model.Cliente;
import com.example.microservicioDeFactura.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }

    public Optional<Cliente> buscarPorRut(String rutEmpresa) {
        return Optional.ofNullable(clienteRepository.findByRutEmpresa(rutEmpresa));
    }
}
