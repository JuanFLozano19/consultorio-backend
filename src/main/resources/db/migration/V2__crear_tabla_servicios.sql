-- ============================================
-- V2: Servicios que se muestran en la web
-- ============================================
CREATE TABLE IF NOT EXISTS servicios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    icono VARCHAR(40) NOT NULL DEFAULT 'stethoscope',
    orden INT NOT NULL DEFAULT 0,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO servicios (nombre, descripcion, icono, orden) VALUES
('Consulta médica general', 'Valoración completa, diagnóstico y tratamiento para adultos y niños.', 'stethoscope', 1),
('Asesoría en actividad física', 'Plan de ejercicio a tu medida: tipo, intensidad, frecuencia y número de repeticiones.', 'pulse', 2),
('Chequeo preventivo', 'Control de presión, peso y factores de riesgo antes de que aparezcan los síntomas.', 'heart', 3),
('Certificados médicos', 'Certificados de salud, aptitud física e incapacidades.', 'document', 4);
