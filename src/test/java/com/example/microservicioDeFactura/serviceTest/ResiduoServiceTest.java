package com.example.microservicioDeFactura.serviceTest;

import com.example.microservicioDeFactura.service.ResiduoService;
import com.example.microservicioDeFactura.controller.ResiduoController;
import com.example.microservicioDeFactura.model.Residuo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ResiduoServiceTest {

    @Mock
    private ResiduoService residuoService;

    @InjectMocks
    private ResiduoController residuoController;

    private Residuo residuo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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
    void testListarResiduos() {
        when(residuoService.findAll()).thenReturn(List.of(residuo));

        var response = residuoController.listar();

        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is3xxRedirection());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(residuo.getId(), response.getBody().get(0).getId());
    }

    @Test
    void testListarResiduosVacio() {
        when(residuoService.findAll()).thenReturn(List.of());

        var response = residuoController.listar();

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testCrearResiduo() {
        doNothing().when(residuoService).guardar(any(Residuo.class));

        var response = residuoController.guardar(residuo);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCrearResiduoNull() {
        var response = residuoController.guardar(null);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testBuscarPorId_found() {
        when(residuoService.findById(1)).thenReturn(Optional.of(residuo));

        var response = residuoController.buscarPorId(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(residuo.getId(), response.getBody().getId());
    }

    @Test
    void testBuscarPorId_notFound() {
        when(residuoService.findById(2)).thenReturn(Optional.empty());

        var response = residuoController.buscarPorId(2);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testEliminarPorId_found() {
        when(residuoService.findById(1)).thenReturn(Optional.of(residuo));
        doNothing().when(residuoService).eliminarPorId(1);

        var response = residuoController.eliminarPorId(1);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testEliminarPorId_notFound() {
        when(residuoService.findById(2)).thenReturn(Optional.empty());

        var response = residuoController.eliminarPorId(2);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testActualizar_found() {
        when(residuoService.findById(1)).thenReturn(Optional.of(residuo));
        doNothing().when(residuoService).actualizar(eq(1), any(Residuo.class));

        var response = residuoController.actualizar(1, residuo);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testActualizar_notFound() {
        when(residuoService.findById(2)).thenReturn(Optional.empty());

        var response = residuoController.actualizar(2, residuo);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testHealth() {
        String result = residuoController.health();
        assertEquals("Service is running!", result);
    }
}
