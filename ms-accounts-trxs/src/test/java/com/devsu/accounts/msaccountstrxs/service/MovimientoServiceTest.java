package com.devsu.accounts.msaccountstrxs.service;

import com.devsu.accounts.msaccountstrxs.model.Cuenta;
import com.devsu.accounts.msaccountstrxs.model.Movimiento;
import com.devsu.accounts.msaccountstrxs.repository.CuentaRepository;
import com.devsu.accounts.msaccountstrxs.repository.MovimientoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceTest {

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private MovimientoService movimientoService;

    @Test
    void testObtenerMovimientosPorCuenta() {
        // Datos de prueba
        Long numeroCuenta = 12345L;

        Movimiento movimiento1 = new Movimiento();
        movimiento1.setMovimiento_id(1L);
        movimiento1.setTipoMovimiento("DEPOSITO");
        movimiento1.setValor(500.0);
        movimiento1.setSaldo(1500.0);

        Movimiento movimiento2 = new Movimiento();
        movimiento2.setMovimiento_id(2L);
        movimiento2.setTipoMovimiento("RETIRO");
        movimiento2.setValor(200.0);
        movimiento2.setSaldo(1300.0);

        when(movimientoRepository.findByCuentaNumeroCuenta(numeroCuenta))
                .thenReturn(List.of(movimiento1, movimiento2));

        // Llamar al servicio
        List<Movimiento> movimientos = movimientoService.obtenerMovimientosPorCuenta(numeroCuenta);

        // Validar resultados
        assertNotNull(movimientos);
        assertEquals(2, movimientos.size());
        verify(movimientoRepository, times(1)).findByCuentaNumeroCuenta(numeroCuenta);
    }

    @Test
    void testGuardarMovimiento() {
        // Datos de prueba
        Long numeroCuenta = 12345L;

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(numeroCuenta);
        cuenta.setSaldoInicial(1000.0); // Saldo inicial

        // Mock del repositorio de cuenta
        when(cuentaRepository.findByNumeroCuenta(numeroCuenta)).thenReturn(Optional.of(cuenta));

        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta); // Asignar la cuenta al movimiento
        movimiento.setTipoMovimiento("DEPOSITO");
        movimiento.setValor(500.0); // Valor del depósito

        // Mock del repositorio de movimiento
        when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(invocation -> {
            Movimiento saved = invocation.getArgument(0);
            saved.setMovimiento_id(1L); // ID simulado
            saved.setSaldo(1500.0); // Saldo esperado después del depósito
            return saved;
        });

        // Llamar al servicio
        Movimiento resultado = movimientoService.guardarMovimiento(movimiento);

        // Validar resultados
        assertNotNull(resultado);
        assertEquals(1L, resultado.getMovimiento_id());
        assertEquals(1500.0, resultado.getSaldo()); // Verificar saldo actualizado
    }




    @Test
    void testGuardarMovimiento_CuentaNoEncontrada() {
        // Datos de prueba
        Long numeroCuenta = 99999L;
        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(new Cuenta());
        movimiento.getCuenta().setNumeroCuenta(numeroCuenta);

        // Stubbing necesario, marcado como lenient
        lenient().when(cuentaRepository.findById(numeroCuenta)).thenReturn(Optional.empty());

        // Llamar al servicio y validar que lanza excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                movimientoService.guardarMovimiento(movimiento)
        );

        assertEquals("Cuenta no encontrada", exception.getMessage());
    }


}
