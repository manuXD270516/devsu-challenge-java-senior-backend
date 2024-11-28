package com.challenge.devsu.ms.client.msclients.service;

import com.challenge.devsu.ms.client.msclients.exceptions.ResourceNotFoundException;
import com.challenge.devsu.ms.client.msclients.model.Cliente;
import com.challenge.devsu.ms.client.msclients.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
    }

    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(clienteActualizado.getNombre());
                    cliente.setDireccion(clienteActualizado.getDireccion());
                    cliente.setTelefono(clienteActualizado.getTelefono());
                    cliente.setContrasena(clienteActualizado.getContrasena());
                    cliente.setEstado(clienteActualizado.isEstado());
                    return clienteRepository.save(cliente);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " no encontrado"));
    }
}
