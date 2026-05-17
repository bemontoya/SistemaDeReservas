CREATE TABLE menus (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(500),
    precio DECIMAL(38, 2) NOT NULL,
    categoria VARCHAR(255) NOT NULL,
    disponible BOOLEAN DEFAULT TRUE
);