
-- Inserta datos en la tabla persona
INSERT INTO persona (id, nombre, genero, edad, identificacion, direccion, telefono) VALUES
                                                                                        (NEXTVAL('persona_sequence'), 'Jose Lema', 'M', 30, '1234567890', 'Otavalo sn y principal', '098254785'),
                                                                                        (NEXTVAL('persona_sequence'), 'Marianela Montalvo', 'F', 28, '0987654321', 'Amazonas y NN.UU.', '097548965'),
                                                                                        (NEXTVAL('persona_sequence'), 'Juan Osorio', 'M', 35, '1122334455', '13 junio y Equinoccial', '098874587');

-- Inserta datos en la tabla cliente, referenciando los IDs de persona
INSERT INTO cliente (id, cliente_id, contrasena, estado) VALUES
                                                             (1, 'CL001', '1234', true),
                                                             (2, 'CL002', '5678', true),
                                                             (3, 'CL003', '1245', true);