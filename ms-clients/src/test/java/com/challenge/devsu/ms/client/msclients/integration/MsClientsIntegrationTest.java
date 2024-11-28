package com.challenge.devsu.ms.client.msclients.integration;

import com.challenge.devsu.ms.client.msclients.model.Cliente;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MsClientsIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    void testClientCreationAndEventPublishing() {
        // Crear cliente
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan Perez");
        cliente.setDireccion("Calle Falsa 123");
        cliente.setGenero("M");
        cliente.setEdad(30);
        cliente.setIdentificacion("1234567890");
        cliente.setTelefono("0987654321");
        cliente.setEstado(true);

        // Enviar POST request para crear cliente
        ResponseEntity<Cliente> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/clientes",
                cliente,
                Cliente.class
        );

        // Validar que el cliente fue creado exitosamente
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNombre()).isEqualTo("Juan Perez");

        // Capturar el argumento enviado a RabbitTemplate
        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);

        verify(rabbitTemplate, times(1)).convertAndSend(
                eq("client-exchange"),
                eq("client-routing-key"),
                captor.capture()
        );

        // Verificar que el argumento capturado tiene los valores esperados
        Object capturedArgument = captor.getValue();
        assertThat(capturedArgument).isInstanceOf(Map.class);

        Map<String, Object> payload = (Map<String, Object>) capturedArgument;
        assertThat(payload.get("tipoEvento")).isEqualTo("CREAR_CLIENTE");
        assertThat(payload.get("nombre")).isEqualTo("Juan Perez");
        assertThat(payload.get("estado")).isEqualTo(true);
    }

}
