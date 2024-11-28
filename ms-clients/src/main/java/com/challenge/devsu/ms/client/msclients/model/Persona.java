package com.challenge.devsu.ms.client.msclients.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persona_seq")
    @SequenceGenerator(name = "persona_seq", sequenceName = "persona_sequence", allocationSize = 1)
    private Long id;

    private String nombre;

    private String genero;

    private int edad;

    private String identificacion;

    private String direccion;

    private String telefono;
}