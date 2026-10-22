-- ============================================
-- V1: Usuarios administradores del consultorio
-- ============================================
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL,
    fecha_registro TIMESTAMP NOT NULL DEFAULT NOW()
);
