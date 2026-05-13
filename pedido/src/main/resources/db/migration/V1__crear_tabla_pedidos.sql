CREATE TABLE pedidos(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    reserva_id BIGINT,
    fecha_pedido DATETIME,
    total DOUBLE,
    estado VARCHAR(50) NOT NULL
);