package com.devsu.accounts.msaccountstrxs.integration;

import com.devsu.accounts.msaccountstrxs.model.Cuenta;
import com.devsu.accounts.msaccountstrxs.model.Movimiento;
import com.devsu.accounts.msaccountstrxs.repository.CuentaRepository;
import com.devsu.accounts.msaccountstrxs.repository.MovimientoRepository;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MovimientoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;


    @BeforeEach
    void setup() {
        movimientoRepository.deleteAll();
        cuentaRepository.deleteAll();

        Cuenta cuenta = new Cuenta();
        cuenta.setClienteId(1L);
        cuenta.setNumeroCuenta(12345L);
        cuenta.setSaldoInicial(1000.0);
        cuenta.setEstado(true);
        cuentaRepository.save(cuenta);
    }

    @Test
    void testCrearMovimiento() {
        // Preparar cuenta de prueba
        Cuenta cuenta = new Cuenta();
        cuenta.setClienteId(1L);
        cuenta.setNumeroCuenta(12345L);
        cuenta.setSaldoInicial(1000.0);
        cuenta.setEstado(true);
        //cuentaRepository.save(cuenta);

        // Preparar movimiento de prueba
        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        //movimiento.setCuenta(cuenta);
        movimiento.setTipoMovimiento("DEPOSITO");
        movimiento.setValor(500.0);
        movimiento.setSaldo(10000.0);

        // Llamar al endpoint REST
        ResponseEntity<Movimiento> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/movimientos",
                movimiento,
                Movimiento.class
        );

        // Validar la respuesta
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getSaldo()).isEqualTo(1500.0);

        // Validar que el movimiento fue registrado en la base de datos
        Awaitility.await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            List<Movimiento> movimientos = movimientoRepository.findAll();
            assertThat(movimientos).hasSize(1);
            Movimiento savedMovimiento = movimientos.get(0);
            assertThat(savedMovimiento.getTipoMovimiento()).isEqualTo("DEPOSITO");
            assertThat(savedMovimiento.getSaldo()).isEqualTo(1500.0);
        });
    }
}
