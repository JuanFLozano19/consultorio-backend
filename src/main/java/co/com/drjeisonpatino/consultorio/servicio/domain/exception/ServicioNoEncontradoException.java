package co.com.drjeisonpatino.consultorio.servicio.domain.exception;

public class ServicioNoEncontradoException extends RuntimeException {
    public ServicioNoEncontradoException(Long id) {
        super("No existe el servicio con id " + id);
    }
}
