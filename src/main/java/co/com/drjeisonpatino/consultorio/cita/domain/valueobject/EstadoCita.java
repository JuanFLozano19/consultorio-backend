package co.com.drjeisonpatino.consultorio.cita.domain.valueobject;

import java.util.Set;

/** Ciclo de vida de una solicitud de cita. */
public enum EstadoCita {
    PENDIENTE,
    CONFIRMADA,
    CANCELADA,
    ATENDIDA;

    public Set<EstadoCita> siguientesPermitidos() {
        return switch (this) {
            case PENDIENTE -> Set.of(CONFIRMADA, CANCELADA);
            case CONFIRMADA -> Set.of(ATENDIDA, CANCELADA);
            case CANCELADA, ATENDIDA -> Set.of();
        };
    }
}
