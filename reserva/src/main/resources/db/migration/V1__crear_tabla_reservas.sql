CREATE TABLE reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    mesa_id BIGINT NOT NULL,
    fecha_reserva DATETIME NOT NULL,
    estado VARCHAR(50) NOT NULL
);