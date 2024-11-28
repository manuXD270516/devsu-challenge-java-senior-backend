package com.challenge.devsu.ms.client.msclients.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cliente")
public class Cliente extends Persona {

    private String clienteId;

    private String contrasena;

    private boolean estado;
}