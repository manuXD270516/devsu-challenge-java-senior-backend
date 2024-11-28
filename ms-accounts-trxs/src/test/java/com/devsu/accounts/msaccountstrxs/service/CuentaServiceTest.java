package com.devsu.accounts.msaccountstrxs.service;

import com.devsu.accounts.msaccountstrxs.model.Cuenta;
import com.devsu.accounts.msaccountstrxs.repository.CuentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuentaServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaService cuentaService;

    @Test
    void testObtenerCuentasPorCliente() {
        // Datos de prueba
        Long clienteId = 1L;

        Cuenta cuenta1 = new Cuenta();
        cuenta1.setNumeroCuenta(12345L);
        cuenta1.setTipoCuenta("Ahorros");
        cuenta1.setSaldoInicial(1000.0);
        cuenta1.setEstado(true);
        cuenta1.setClienteId(clienteId);

        Cuenta cuenta2 = new Cuenta();
        cuenta2.setNumeroCuenta(67890L);
        cuenta2.setTipoCuenta("Corriente");
        cuenta2.setSaldoInicial(500.0);
        cuenta2.setEstado(true);
        cuenta2.setClienteId(clienteId);

        when(cuentaRepository.findByClienteId(clienteId)).thenReturn(List.of(cuenta1, cuenta2));

        // Llamar al servicio
        List<Cuenta> cuentas = cuentaService.obtenerCuentasPorCliente(clienteId);

        // Validar resultados
        assertNotNull(cuentas);
        assertEquals(2, cuentas.size());
        verify(cuentaRepository, times(1)).findByClienteId(clienteId);
    }

    @Test
    void testGuardarCuenta() {
        // Datos de prueba
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setSaldoInicial(1500.0);
        cuenta.setEstado(true);
        cuenta.setClienteId(1L);

        when(cuentaRepository.save(any(Cuenta.class))).thenAnswer(invocation -> {
            Cuenta saved = invocation.getArgument(0);
            saved.setNumeroCuenta(12345L);
            return saved;
        });

        // Llamar al servicio
        Cuenta resultado = cuentaService.guardarCuenta(cuenta);

        // Validar resultados
        assertNotNull(resultado);
        assertEquals(12345L, resultado.getNumeroCuenta());
        verify(cuentaRepository, times(1)).save(cuenta);
    }
}
