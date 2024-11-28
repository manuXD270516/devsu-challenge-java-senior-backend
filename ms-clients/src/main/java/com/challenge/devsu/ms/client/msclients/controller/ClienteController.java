package com.challenge.devsu.ms.client.msclients.controller;

import com.challenge.devsu.ms.client.msclients.model.Cliente;
import com.challenge.devsu.ms.client.msclients.service.ClientEventPublisher;
import com.challenge.devsu.ms.client.msclients.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> obtenerTodos() {
        return clienteService.obtenerTodos();
    }

    @Autowired
    private ClientEventPublisher clientEventPublisher;

    @PostMapping
    public Cliente guardarCliente(@RequestBody Cliente cliente) {
        Cliente clienteGuardado = clienteService.guardarCliente(cliente);
        clientEventPublisher.publishClientEvent(
                clienteGuardado.getId(),
                clienteGuardado.getNombre(),
                clienteGuardado.isEstado()
        );
        return clienteGuardado;
    }

    @GetMapping("/{id}")
    public Cliente obtenerPorId(@PathVariable Long id) {
        return clienteService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente clienteActualizado = clienteService.actualizarCliente(id, cliente);

        clientEventPublisher.publishClientEvent(
                clienteActualizado.getId(),
                clienteActualizado.getNombre(),
                clienteActualizado.isEstado()
        );

        return ResponseEntity.ok(clienteActualizado);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        clientEventPublisher.publishClientEvent(id, "Cliente eliminado", false);
        return ResponseEntity.noContent().build();
    }
}
