package com.example.microservicioDeFactura.controller;

import com.example.microservicioDeFactura.model.Cliente;
import com.example.microservicioDeFactura.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


      // GET: Listar todos los clientes

    @GetMapping("/listarTodos")
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }

    // GET: Obtener cliente por ID

    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // POST: Crear un nuevo cliente

    @PostMapping("/crearCliente")
    public ResponseEntity<Cliente> crear(@RequestBody Cliente cliente) {
        Cliente nuevo = clienteService.guardar(cliente);
        return ResponseEntity.ok(nuevo);
    }

      // PUT: Actualizar cliente por ID

    @PutMapping("/actualizarClientePorId/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.buscarPorId(id)
                .map(existente -> {
                    cliente.setId(id);
                    Cliente actualizado = clienteService.guardar(cliente);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Eliminar cliente por ID
    
    @DeleteMapping("/eliminarClientePorId/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (clienteService.buscarPorId(id).isPresent()) {
            clienteService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/obtenerPorRut/{rutEmpresa}")
    public ResponseEntity<Cliente> obtenerPorRut(@PathVariable String rutEmpresa) {
        return clienteService.buscarPorRut(rutEmpresa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
