package com.example.microservicioDeFactura.serviceTest;

import com.example.microservicioDeFactura.model.Cliente;
import com.example.microservicioDeFactura.repository.ClienteRepository;
import com.example.microservicioDeFactura.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitarios para el servicio ClienteService.
 * Verifica la lógica de negocio y la interacción con el repositorio de clientes.
 */
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    /**
     * Inicializa un cliente de prueba antes de cada test.
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
    }

    /**
     * Prueba listar todos los clientes.
     */
    @Test
    void testListarTodos() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        List<Cliente> clientes = clienteService.listarTodos();
        assertEquals(1, clientes.size());
        assertEquals(cliente.getNombreEmpresa(), clientes.get(0).getNombreEmpresa());
    }

    /**
     * Prueba buscar un cliente por ID (encontrado).
     */
    @Test
    void testBuscarPorId_found() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        Optional<Cliente> result = clienteService.buscarPorId(1L);
        assertTrue(result.isPresent());
        assertEquals(cliente.getRutEmpresa(), result.get().getRutEmpresa());
    }

    /**
     * Prueba buscar un cliente por ID (no encontrado).
     */
    @Test
    void testBuscarPorId_notFound() {
        when(clienteRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Cliente> result = clienteService.buscarPorId(2L);
        assertFalse(result.isPresent());
    }

    /**
     * Prueba guardar un cliente.
     */
    @Test
    void testGuardar() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        Cliente guardado = clienteService.guardar(cliente);
        assertNotNull(guardado);
        assertEquals(cliente.getCorreo(), guardado.getCorreo());
    }

    /**
     * Prueba eliminar un cliente por ID.
     */
    @Test
    void testEliminar() {
        doNothing().when(clienteRepository).deleteById(1L);
        clienteService.eliminar(1L);
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    /**
     * Prueba buscar un cliente por RUT (encontrado).
     */
    @Test
    void testBuscarPorRut_found() {
        when(clienteRepository.findByRutEmpresa("12345678-9")).thenReturn(cliente);
        Optional<Cliente> result = clienteService.buscarPorRut("12345678-9");
        assertTrue(result.isPresent());
        assertEquals(cliente.getNombreEmpresa(), result.get().getNombreEmpresa());
    }

    /**
     * Prueba buscar un cliente por RUT (no encontrado).
     */
    @Test
    void testBuscarPorRut_notFound() {
        when(clienteRepository.findByRutEmpresa("00000000-0")).thenReturn(null);
        Optional<Cliente> result = clienteService.buscarPorRut("00000000-0");
        assertFalse(result.isPresent());
    }
}
