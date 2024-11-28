-- Población de la tabla `cuenta`
INSERT INTO cuenta (id, numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id) VALUES
(NEXTVAL('cuenta_sequence'), 478758, 'Ahorros', 2000, true, 1), -- Cuenta de Jose Lema
(NEXTVAL('cuenta_sequence'), 225487, 'Corriente', 100, true, 2), -- Cuenta de Marianela Montalvo
(NEXTVAL('cuenta_sequence'), 495878, 'Ahorros', 0, true, 3), -- Cuenta de Juan Osorio
(NEXTVAL('cuenta_sequence'), 496825, 'Ahorros', 540, true, 2), -- Otra cuenta de Marianela Montalvo
(NEXTVAL('cuenta_sequence'), 585545, 'Corriente', 1000, true, 1); -- Nueva cuenta para Jose Lema

-- Población de la tabla `movimiento`
INSERT INTO movimiento (movimiento_id, fecha, tipo_movimiento, valor, saldo, cuenta_id) VALUES
(NEXTVAL('movimiento_sequence'), '2022-10-02', 'Retiro', 575, 1425, 1), -- Movimiento en cuenta de Jose Lema
(NEXTVAL('movimiento_sequence'), '2022-10-02', 'Depósito', 600, 700, 2), -- Movimiento en cuenta de Marianela Montalvo
(NEXTVAL('movimiento_sequence'), '2022-10-03', 'Depósito', 150, 150, 1), -- Movimiento en cuenta de Juan Osorio
(NEXTVAL('movimiento_sequence'), '2022-10-03', 'Retiro', 540, 0, 3); -- Movimiento en cuenta de Marianela Montalvo
