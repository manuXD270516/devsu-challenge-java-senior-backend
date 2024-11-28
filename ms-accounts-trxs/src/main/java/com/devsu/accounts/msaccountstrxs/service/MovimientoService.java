package com.devsu.accounts.msaccountstrxs.service;

import com.devsu.accounts.msaccountstrxs.exceptions.ResourceNotFoundException;
import com.devsu.accounts.msaccountstrxs.model.Cuenta;
import com.devsu.accounts.msaccountstrxs.model.Movimiento;
import com.devsu.accounts.msaccountstrxs.repository.CuentaRepository;
import com.devsu.accounts.msaccountstrxs.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Movimiento> obtenerMovimientosPorCuenta(Long numeroCuenta) {
        return movimientoRepository.findByCuentaNumeroCuenta(numeroCuenta);
    }

    public Movimiento guardarMovimiento(Movimiento movimiento) {
        if (movimiento.getCuenta() == null) {
            throw new IllegalArgumentException("La cuenta asociada al movimiento no puede ser nula");
        }
        // Verificar que la cuenta exista
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimiento.getCuenta().getNumeroCuenta())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada"));

        // Calcular el nuevo saldo basado en el tipo de movimiento
        if ("DEPOSITO".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            cuenta.setSaldoInicial(cuenta.getSaldoInicial() + movimiento.getValor());
        } else if ("RETIRO".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            if (cuenta.getSaldoInicial() < movimiento.getValor()) {
                throw new RuntimeException("Saldo insuficiente");
            }
            cuenta.setSaldoInicial(cuenta.getSaldoInicial() - movimiento.getValor());
        }

        // Actualizar el saldo en el movimiento
        movimiento.setSaldo(cuenta.getSaldoInicial());
        movimiento.setFecha(LocalDate.now());
        movimiento.setCuenta(cuenta);

        // Guardar los cambios en la cuenta
        cuentaRepository.save(cuenta);

        // Guardar el movimiento
        return movimientoRepository.save(movimiento);
    }
}
