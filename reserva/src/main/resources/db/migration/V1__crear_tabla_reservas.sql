CREATE TABLE reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    mesa_id BIGINT NOT NULL,
    fecha_reserva TIMESTAMP NOT NULL,
    cantidad_personas INT NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'CONFIRMADA'
);