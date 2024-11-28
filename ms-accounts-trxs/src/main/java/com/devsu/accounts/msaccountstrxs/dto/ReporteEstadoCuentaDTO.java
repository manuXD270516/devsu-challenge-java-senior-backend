package com.devsu.accounts.msaccountstrxs.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ReporteEstadoCuentaDTO {

    private Long clienteId;

    private String clienteNombre;

    private List<CuentaReporteDTO> cuentas;


    @Data
    @Builder
    public static class CuentaReporteDTO {

        private Long numeroCuenta;

        private String tipoCuenta;

        private double saldo;

        private List<MovimientoDTO> movimientos;

    }

    @Data
    @Builder
    public static class MovimientoDTO {

        private LocalDate fecha;

        private String tipoMovimiento; // Deposito o Retiro

        private double valor;

        private double saldo;

    }
}
