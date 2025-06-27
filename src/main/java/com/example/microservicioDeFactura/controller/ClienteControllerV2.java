package com.example.microservicioDeFactura.controller;

import com.example.microservicioDeFactura.assemblers.ClienteModelAssembler;
import com.example.microservicioDeFactura.model.Cliente;
import com.example.microservicioDeFactura.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Controlador REST HATEOAS para operaciones CRUD sobre Clientes (v2).
 */
@RestController
@RequestMapping("/api/v2/clientes")
public class ClienteControllerV2 {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteModelAssembler clienteModelAssembler;

    /**
     * Lista todos los clientes con enlaces HATEOAS.
     */
    @GetMapping
    public CollectionModel<EntityModel<Cliente>> listarTodos() {
        List<EntityModel<Cliente>> clientes = clienteService.listarTodos().stream()
                .map(clienteModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(clientes,
                linkTo(methodOn(ClienteControllerV2.class).listarTodos()).withSelfRel());
    }

    /**
     * Obtiene un cliente por su ID con enlaces HATEOAS.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> obtenerPorId(@PathVariable Long id) {
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(id);
        return clienteOpt
                .map(clienteModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo cliente y retorna el recurso con enlaces HATEOAS.
     */
    @PostMapping
    public ResponseEntity<EntityModel<Cliente>> crear(@RequestBody Cliente cliente) {
        Cliente nuevo = clienteService.guardar(cliente);
        EntityModel<Cliente> clienteModel = clienteModelAssembler.toModel(nuevo);
        return ResponseEntity
                .created(clienteModel.getRequiredLink("self").toUri())
                .body(clienteModel);
    }

    /**
     * Actualiza un cliente existente y retorna el recurso actualizado con enlaces HATEOAS.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.buscarPorId(id)
                .map(existente -> {
                    cliente.setId(id);
                    Cliente actualizado = clienteService.guardar(cliente);
                    EntityModel<Cliente> clienteModel = clienteModelAssembler.toModel(actualizado);
                    return ResponseEntity.ok(clienteModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un cliente por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (clienteService.buscarPorId(id).isPresent()) {
            clienteService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Obtiene un cliente por su RUT de empresa con enlaces HATEOAS.
     */
    @GetMapping("/rut/{rutEmpresa}")
    public ResponseEntity<EntityModel<Cliente>> obtenerPorRut(@PathVariable String rutEmpresa) {
        Optional<Cliente> clienteOpt = clienteService.buscarPorRut(rutEmpresa);
        return clienteOpt
                .map(clienteModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
