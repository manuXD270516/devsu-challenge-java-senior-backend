CREATE TABLE IF NOT EXISTS persona (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    genero VARCHAR(1),
    edad INT,
    identificacion VARCHAR(20),
    direccion VARCHAR(255),
    telefono VARCHAR(20)
);
CREATE SEQUENCE IF NOT EXISTS persona_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS cliente (
    id BIGINT PRIMARY KEY,
    cliente_id VARCHAR(20),
    contrasena VARCHAR(100),
    estado BOOLEAN,
    FOREIGN KEY (id) REFERENCES persona (id)
);
