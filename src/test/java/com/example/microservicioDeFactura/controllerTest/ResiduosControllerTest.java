package com.example.microservicioDeFactura.controllerTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.example.microservicioDeFactura.service.ResiduoService;
import com.example.microservicioDeFactura.controller.ResiduoController;
import com.example.microservicioDeFactura.model.Residuo;
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

@WebMvcTest(ResiduoController.class)
public class ResiduosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResiduoService residuoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Residuo residuo;

    @BeforeEach
    void setUp() {
        residuo = new Residuo();
        residuo.setId(1);
        residuo.setNombre("Residuo Test");
        residuo.setTipo("Orgánico");
        residuo.setPeso(50.0);
        residuo.setUnidadMedida("kg");
        residuo.setPeligrosidad("Baja");
        residuo.setEmpresaEmisora("Empresa Residuo");
        residuo.setVolumen(10.0);
        residuo.setClasificacion("Reciclable");
    }

    @Test
    void testListarResiduos() throws Exception {
        when(residuoService.findAll()).thenReturn(List.of(residuo));

        mockMvc.perform(get("/api/residuos/v1/listarResiduos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(residuo.getId()))
                .andExpect(jsonPath("$[0].nombre").value(residuo.getNombre()));
    }

    @Test
    void testBuscarPorId_found() throws Exception {
        when(residuoService.findById(1)).thenReturn(Optional.of(residuo));

        mockMvc.perform(get("/api/residuos/v1/buscarPorId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(residuo.getId()))
                .andExpect(jsonPath("$.nombre").value(residuo.getNombre()));
    }

    @Test
    void testBuscarPorId_notFound() throws Exception {
        when(residuoService.findById(2)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/residuos/v1/buscarPorId/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarPorId_found() throws Exception {
        when(residuoService.findById(1)).thenReturn(Optional.of(residuo));
        doNothing().when(residuoService).eliminarPorId(1);

        mockMvc.perform(delete("/api/residuos/v1/eliminarPorId/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarPorId_notFound() throws Exception {
        when(residuoService.findById(2)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/residuos/v1/eliminarPorId/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGuardarResiduos() throws Exception {
        doNothing().when(residuoService).guardar(any(Residuo.class));

        mockMvc.perform(post("/api/residuos/v1/guardarResiduos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(residuo)))
                .andExpect(status().isOk());
    }

    @Test
    void testGuardarResiduos_badRequest() throws Exception {
        mockMvc.perform(post("/api/residuos/v1/guardarResiduos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testActualizarResiduos_found() throws Exception {
        when(residuoService.findById(1)).thenReturn(Optional.of(residuo));
        doNothing().when(residuoService).actualizar(eq(1), any(Residuo.class));

        mockMvc.perform(patch("/api/residuos/v1/actualizarResiduos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(residuo)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarResiduos_notFound() throws Exception {
        when(residuoService.findById(2)).thenReturn(Optional.empty());

        mockMvc.perform(patch("/api/residuos/v1/actualizarResiduos/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(residuo)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testHealth() throws Exception {
        mockMvc.perform(get("/api/residuos/v1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Service is running!"));
    }
}