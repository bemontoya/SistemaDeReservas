CREATE TABLE inventarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_ingrediente VARCHAR(255) NOT NULL,
    cantidad_actual INT NOT NULL,
    stock_minimo INT NOT NULL,
    unidad_medida VARCHAR(255) NOT NULL
);