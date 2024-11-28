package com.devsu.accounts.msaccountstrxs.service;

import com.devsu.accounts.msaccountstrxs.dto.ReporteEstadoCuentaDTO;
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
public class ReporteService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    public ReporteEstadoCuentaDTO generarReporte(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        // Obtener cuentas del cliente
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);

        if (cuentas.isEmpty()) {
            throw new ResourceNotFoundException("El cliente no tiene cuentas registradas.");
        }

        // Construir el reporte
        List<ReporteEstadoCuentaDTO.CuentaReporteDTO> cuentasReporte = cuentas.stream().map(cuenta -> {
            // Obtener movimientos de la cuenta dentro del rango de fechas
            List<Movimiento> movimientos = movimientoRepository.findByCuentaNumeroCuentaAndFechaBetween(
                    cuenta.getNumeroCuenta(), fechaInicio, fechaFin);

            // Mapear los movimientos
            List<ReporteEstadoCuentaDTO.MovimientoDTO> movimientosDTO = movimientos.stream().map(mov ->
                    ReporteEstadoCuentaDTO.MovimientoDTO.builder()
                            .fecha(mov.getFecha())
                            .tipoMovimiento(mov.getTipoMovimiento())
                            .valor(mov.getValor())
                            .saldo(mov.getSaldo())
                            .build()
            ).toList();

            return ReporteEstadoCuentaDTO.CuentaReporteDTO.builder()
                    .numeroCuenta(cuenta.getNumeroCuenta())
                    .tipoCuenta(cuenta.getTipoCuenta())
                    .saldo(cuenta.getSaldoInicial())
                    .movimientos(movimientosDTO)
                    .build();
        }).toList();

        return ReporteEstadoCuentaDTO.builder()
                .clienteId(clienteId)
                .clienteNombre(cuentas.get(0).getClienteId().toString()) // Suponer que el nombre está asociado al cliente
                .cuentas(cuentasReporte)
                .build();
    }
}
