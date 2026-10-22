package co.com.drjeisonpatino.consultorio.cita.domain.exception;

import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.EstadoCita;

public class TransicionEstadoInvalidaException extends RuntimeException {
    public TransicionEstadoInvalidaException(EstadoCita actual, EstadoCita nuevo) {
        super("No se puede pasar una cita de " + actual + " a " + nuevo);
    }
}
