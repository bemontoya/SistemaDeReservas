-- =============================================
-- Script de migración inicial - ms-clientes
-- Base de datos: db_clientes
-- =============================================

CREATE DATABASE IF NOT EXISTS db_clientes;
USE db_clientes;

CREATE TABLE IF NOT EXISTS clientes (
    id_cliente  BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    apellido    VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    telefono    VARCHAR(20)  NOT NULL,
    activo      BOOLEAN      NOT NULL DEFAULT TRUE
);

-- Datos de prueba iniciales
INSERT INTO clientes (nombre, apellido, email, telefono, activo) VALUES
    ('Juan', 'Pérez',    'juan.perez@mail.com',    '+56912345678', TRUE),
    ('María', 'González','maria.gonzalez@mail.com', '+56987654321', TRUE),
    ('Carlos', 'López',  'carlos.lopez@mail.com',   '+56911223344', TRUE);
