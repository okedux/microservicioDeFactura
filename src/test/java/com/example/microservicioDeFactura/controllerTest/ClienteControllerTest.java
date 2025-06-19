package com.example.microservicioDeFactura.controllerTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.example.microservicioDeFactura.controller.ClienteController;
import com.example.microservicioDeFactura.model.Cliente;
import com.example.microservicioDeFactura.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombreEmpresa("Empresa Test");
        cliente.setRutEmpresa("12345678-9");
        cliente.setContacto("Juan Pérez");
        cliente.setCorreo("juan.perez@test.com");
        cliente.setDireccion("Calle Falsa 123");
    }

    @Test
    void testListarTodos() throws Exception {
        when(clienteService.listarTodos()).thenReturn(List.of(cliente));

        mockMvc.perform(get("/api/v1/clientes/listarTodos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(cliente.getId()))
                .andExpect(jsonPath("$[0].nombreEmpresa").value(cliente.getNombreEmpresa()));
    }

    @Test
    void testObtenerPorId() throws Exception {
        when(clienteService.buscarPorId(cliente.getId())).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/api/v1/clientes/obtenerPorId/{id}", cliente.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.nombreEmpresa").value(cliente.getNombreEmpresa()));
    }

    @Test
    void testCrearCliente() throws Exception {
        when(clienteService.guardar(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/v1/clientes/crearCliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.nombreEmpresa").value(cliente.getNombreEmpresa()));
    }

    @Test
    void testActualizarCliente() throws Exception {
        when(clienteService.buscarPorId(cliente.getId())).thenReturn(Optional.of(cliente));
        when(clienteService.guardar(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(put("/api/v1/clientes/actualizarClientePorId/{id}", cliente.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.nombreEmpresa").value(cliente.getNombreEmpresa()));
    }

    @Test
    void testEliminarCliente() throws Exception {
        when(clienteService.buscarPorId(cliente.getId())).thenReturn(Optional.of(cliente));
        doNothing().when(clienteService).eliminar(cliente.getId());

        mockMvc.perform(delete("/api/v1/clientes/eliminarClientePorId/{id}", cliente.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testObtenerPorRut() throws Exception {
        when(clienteService.buscarPorRut(cliente.getRutEmpresa())).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/api/v1/clientes/obtenerPorRut/{rutEmpresa}", cliente.getRutEmpresa()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.nombreEmpresa").value(cliente.getNombreEmpresa()));
    }
}