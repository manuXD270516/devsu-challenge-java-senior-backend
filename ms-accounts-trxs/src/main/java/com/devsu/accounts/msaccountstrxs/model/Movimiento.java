package com.devsu.accounts.msaccountstrxs.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "movimiento")
//sonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignorar proxies de Hibernate
public class Movimiento {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimiento_seq")
    @SequenceGenerator(name = "movimiento_seq", sequenceName = "movimiento_sequence", allocationSize = 1)
    private Long movimiento_id;

    private LocalDate fecha;

    private String tipoMovimiento; // Deposito, Retiro

    private double valor;

    private double saldo; // Saldo después del movimiento

    @ManyToOne/*(fetch = FetchType.LAZY, optional = false)*/
    @JoinColumn(name = "cuenta_id")
    //@JsonIgnore
    private Cuenta cuenta; // Relación con la cuenta
}
