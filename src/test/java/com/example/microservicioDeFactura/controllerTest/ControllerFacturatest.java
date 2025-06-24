package com.example.microservicioDeFactura.controllerTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.example.microservicioDeFactura.service.facturaService;
import com.example.microservicioDeFactura.controller.controllerFactura;
import com.example.microservicioDeFactura.model.Factura;
import com.example.microservicioDeFactura.model.Cliente;
import com.example.microservicioDeFactura.model.Residuo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Test de integración para el controlador controllerFacturaV2.
 * Verifica los endpoints CRUD y de consulta de facturas.
 */
@WebMvcTest(controllerFactura.class)
public class ControllerFacturatest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private facturaService facturaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Factura factura;

    /**
     * Inicializa una factura de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombreEmpresa("Empresa Test");
        cliente.setRutEmpresa("12345678-9");
        cliente.setContacto("Juan Pérez");
        cliente.setCorreo("juan.perez@test.com");
        cliente.setDireccion("Calle Falsa 123");

        Residuo residuo = new Residuo();
        residuo.setId(1);
        residuo.setNombre("Residuo Test");
        residuo.setTipo("Orgánico");
        residuo.setPeso(50.0);
        residuo.setUnidadMedida("kg");
        residuo.setPeligrosidad("Baja");
        residuo.setEmpresaEmisora("Empresa Residuo");
        residuo.setVolumen(10.0);
        residuo.setClasificacion("Reciclable");

        factura = new Factura();
        factura.setId(1);
        factura.setCliente(cliente);
        factura.setValor(1000);
        factura.setCantidadDesechos(5.0f);
        factura.setResiduo(residuo);
        factura.setFechaEmision(new Date());
    }

    /**
     * Prueba el endpoint para listar todas las facturas.
     */
    @Test
    void testListarFacturas() throws Exception {
        when(facturaService.listarFacturas()).thenReturn(List.of(factura));

        mockMvc.perform(get("/api/factura/v1/listar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(factura.getId()))
                .andExpect(jsonPath("$[0].valor").value(factura.getValor()));
    }

    /**
     * Prueba el endpoint para buscar facturas por RUT de empresa.
     */
    @Test
    void testBuscarPorRutEmpresa() throws Exception {
        when(facturaService.buscarPorRutEmpresa(factura.getCliente().getRutEmpresa())).thenReturn(List.of(factura));

        mockMvc.perform(get("/api/factura/v1/buscarPorRut/{rutEmpresa}", factura.getCliente().getRutEmpresa()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(factura.getId()))
                .andExpect(jsonPath("$[0].cliente.rutEmpresa").value(factura.getCliente().getRutEmpresa()));
    }

    /**
     * Prueba el endpoint para buscar facturas por ID.
     */
    @Test
    void testBuscarPorId() throws Exception {
        when(facturaService.buscarPorId(factura.getId())).thenReturn(Optional.of(factura));

        mockMvc.perform(get("/api/factura/v1/buscarPorId/{id}", factura.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(factura.getId()))
                .andExpect(jsonPath("$[0].valor").value(factura.getValor()));
    }

    /**
     * Prueba el endpoint para guardar una nueva factura.
     */
    @Test
    void testGuardarFactura() throws Exception {
        doNothing().when(facturaService).guardarFactura(any(Factura.class));

        mockMvc.perform(post("/api/factura/v1/guardarFactura")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(factura)))
                .andExpect(status().isOk());
    }

    /**
     * Prueba el endpoint para actualizar una factura existente.
     */
    @Test
    void testActualizarFactura() throws Exception {
        when(facturaService.buscarPorId(factura.getId())).thenReturn(Optional.of(factura));
        doNothing().when(facturaService).guardarFactura(any(Factura.class));

        mockMvc.perform(patch("/api/factura/v1/actualizarFactura/{id}", factura.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(factura)))
                .andExpect(status().isOk());
    }

    /**
     * Prueba el endpoint para eliminar una factura por su ID.
     */
    @Test
    void testEliminarPorId() throws Exception {
        doNothing().when(facturaService).eliminarPorId(factura.getId());

        mockMvc.perform(delete("/api/factura/v1/eliminarPorId/{idEliminar}", factura.getId()))
                .andExpect(status().isNoContent());
    }
}