-- Tabla para las cuentas
CREATE TABLE IF NOT EXISTS cuenta (
                        numero_cuenta BIGINT AUTO_INCREMENT PRIMARY KEY,
                        tipo_cuenta VARCHAR(50) NOT NULL, -- Tipo de cuenta (Ahorros, Corriente, etc.)
                        saldo_inicial DOUBLE NOT NULL,    -- Saldo inicial de la cuenta
                        estado BOOLEAN NOT NULL,          -- Estado de la cuenta (Activa/Inactiva)
                        cliente_id BIGINT NOT NULL        -- ID del cliente asociado (del MS1)
);
CREATE SEQUENCE IF NOT EXISTS cuenta_sequence START WITH 1 INCREMENT BY 1;

-- Tabla para los movimientos
CREATE TABLE IF NOT EXISTS movimiento (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            fecha DATE NOT NULL,             -- Fecha del movimiento
                            tipo_movimiento VARCHAR(50) NOT NULL, -- Tipo de movimiento (Depósito, Retiro)
                            valor DOUBLE NOT NULL,           -- Valor del movimiento
                            saldo DOUBLE NOT NULL,           -- Saldo después del movimiento
                            cuenta_id BIGINT NOT NULL,   -- ID de la cuenta asociada
                            CONSTRAINT fk_movimiento_cuenta FOREIGN KEY (numero_cuenta) REFERENCES cuenta (numero_cuenta)
);
CREATE SEQUENCE IF NOT EXISTS movimiento_sequence START WITH 1 INCREMENT BY 1;

-- Tabla para event logs
CREATE TABLE IF NOT EXISTS event_logs (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            event_type VARCHAR(50) NOT NULL,
                            client_id BIGINT,
                            timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            status VARCHAR(20) NOT NULL,
                            details TEXT
);
CREATE SEQUENCE IF NOT EXISTS event_logs_sequence START WITH 1 INCREMENT BY 1;
