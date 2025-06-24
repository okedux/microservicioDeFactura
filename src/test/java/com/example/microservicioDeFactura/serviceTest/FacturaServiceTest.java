package com.example.microservicioDeFactura.serviceTest;

import com.example.microservicioDeFactura.model.Factura;
import com.example.microservicioDeFactura.repository.FacturaRepository;
import com.example.microservicioDeFactura.service.facturaService;
import com.example.microservicioDeFactura.model.Cliente;
import com.example.microservicioDeFactura.model.Residuo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test unitarios para el servicio facturaService.
 * Verifica la lógica de negocio y la interacción con el repositorio de facturas.
 */
class FacturaServiceTest {

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private facturaService clFacturaService;

    private Factura factura;
    private Cliente cliente;
    private Residuo residuo;

    /**
     * Inicializa los objetos de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombreEmpresa("Empresa Test");
        cliente.setRutEmpresa("12345678-9");
        cliente.setContacto("Juan Perez");
        cliente.setCorreo("test@empresa.com");
        cliente.setDireccion("Calle Falsa 123");

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

        factura = new Factura();
        factura.setId(1);
        factura.setCliente(cliente);
        factura.setValor(1000);
        factura.setFechaEmision(new Date());
        factura.setResiduo(residuo);
    }

    /**
     * Prueba listar todas las facturas.
     */
    @Test
    void testListarFacturas() {
        when(facturaRepository.findAll()).thenReturn(List.of(factura));
        List<Factura> facturas = clFacturaService.listarFacturas();
        assertEquals(1, facturas.size());
        assertEquals(factura.getId(), facturas.get(0).getId());
    }

    /**
     * Prueba buscar facturas por RUT de empresa.
     */
    @Test
    void testBuscarPorRutEmpresa() {
        when(facturaRepository.buscarPorRutEmpresa("12345678-9")).thenReturn(List.of(factura));
        List<Factura> facturas = clFacturaService.buscarPorRutEmpresa("12345678-9");
        assertEquals(1, facturas.size());
        assertEquals("Empresa Test", facturas.get(0).getCliente().getNombreEmpresa());
    }

    /**
     * Prueba buscar facturas por ID.
     */
    @Test
    void testBuscarPorId() {
        when(facturaRepository.buscarPorId(1)).thenReturn(Optional.of(factura));
        Optional<Factura> result = clFacturaService.buscarPorId(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    /**
     * Prueba eliminar una factura por su ID.
     */
    @Test
    void testEliminarPorId() {
        doNothing().when(facturaRepository).eliminarPorId(1);
        clFacturaService.eliminarPorId(1);
        verify(facturaRepository, times(1)).eliminarPorId(1);
    }

    /**
     * Prueba guardar una factura.
     */
    @Test
    void testGuardarFactura() {
        when(facturaRepository.save(any(Factura.class))).thenReturn(factura);
        clFacturaService.guardarFactura(factura);
        verify(facturaRepository, times(1)).save(factura);
    }
}