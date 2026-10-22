-- ============================================
-- V3: Solicitudes de cita hechas desde la web
-- ============================================
CREATE TABLE IF NOT EXISTS citas (
    id BIGSERIAL PRIMARY KEY,
    nombre_paciente VARCHAR(150) NOT NULL,
    documento VARCHAR(20) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(255),
    servicio_id BIGINT REFERENCES servicios(id),
    fecha_preferida DATE NOT NULL,
    franja VARCHAR(10) NOT NULL,
    motivo VARCHAR(500),
    estado VARCHAR(15) NOT NULL DEFAULT 'PENDIENTE',
    acepta_tratamiento_datos BOOLEAN NOT NULL,
    notas_internas VARCHAR(1000),
    fecha_solicitud TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_citas_franja CHECK (franja IN ('MANANA', 'TARDE')),
    CONSTRAINT chk_citas_estado CHECK (estado IN ('PENDIENTE', 'CONFIRMADA', 'CANCELADA', 'ATENDIDA'))
);

CREATE INDEX IF NOT EXISTS idx_citas_estado ON citas(estado);
CREATE INDEX IF NOT EXISTS idx_citas_fecha_preferida ON citas(fecha_preferida);
