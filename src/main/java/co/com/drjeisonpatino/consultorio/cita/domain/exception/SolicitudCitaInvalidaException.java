package co.com.drjeisonpatino.consultorio.cita.domain.exception;

public class SolicitudCitaInvalidaException extends RuntimeException {
    public SolicitudCitaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
