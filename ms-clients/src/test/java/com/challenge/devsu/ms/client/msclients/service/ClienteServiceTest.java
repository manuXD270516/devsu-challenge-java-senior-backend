package com.challenge.devsu.ms.client.msclients.service;

import com.challenge.devsu.ms.client.msclients.model.Cliente;
import com.challenge.devsu.ms.client.msclients.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    public ClienteServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodosClientes() {
        // Datos simulados
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNombre("Jose Lema");

        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNombre("Marianela Montalvo");

        // Simulación de comportamiento del repositorio
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        // Ejecutar prueba
        List<Cliente> clientes = clienteService.obtenerTodos();
        assertNotNull(clientes);
        assertEquals(2, clientes.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void guardarCliente() {
        // Datos simulados
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan Osorio");

        // Simulación de comportamiento del repositorio
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Ejecutar prueba
        Cliente resultado = clienteService.guardarCliente(cliente);
        assertNotNull(resultado);
        assertEquals("Juan Osorio", resultado.getNombre());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void obtenerClientePorId() {
        // Datos simulados
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Jose Lema");

        // Simulación de comportamiento del repositorio
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // Ejecutar prueba
        Cliente resultado = clienteService.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals("Jose Lema", resultado.getNombre());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void eliminarCliente() {
        // Ejecutar prueba
        clienteService.eliminarCliente(1L);

        // Verificar que se llamó al método deleteById
        verify(clienteRepository, times(1)).deleteById(1L);
    }
}
