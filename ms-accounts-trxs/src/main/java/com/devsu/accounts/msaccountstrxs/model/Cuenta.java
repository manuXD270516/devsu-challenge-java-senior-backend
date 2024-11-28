package com.devsu.accounts.msaccountstrxs.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cuenta")
public class Cuenta {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cuenta_seq")
    @SequenceGenerator(name = "cuenta_seq", sequenceName = "cuenta_sequence", allocationSize = 1)
    private Long id;

    private Long numeroCuenta;

    private String tipoCuenta; // Ahorros, Corriente, etc.

    private double saldoInicial;

    private boolean estado; // Activa o inactiva

    private Long clienteId; // ID del cliente (referenciado desde MS1)
}
