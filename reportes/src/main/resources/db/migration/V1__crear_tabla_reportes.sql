CREATE TABLE reportes(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    fecha_generacion DATETIME NOT NULL,
    contenido TEXT,
    generado_por VARCHAR(100) NOT NULL
);