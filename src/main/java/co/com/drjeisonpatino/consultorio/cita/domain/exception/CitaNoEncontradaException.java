package co.com.drjeisonpatino.consultorio.cita.domain.exception;

public class CitaNoEncontradaException extends RuntimeException {
    public CitaNoEncontradaException(Long id) {
        super("No existe la cita con id " + id);
    }
}
