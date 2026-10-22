package co.com.drjeisonpatino.consultorio.servicio.ports.input;

import co.com.drjeisonpatino.consultorio.servicio.application.dto.response.ServicioResponse;

import java.util.List;

public interface ListarServiciosUseCase {
    /** Solo los activos, en orden: lo que ve el público. */
    List<ServicioResponse> listarActivos();

    /** Todos, para el panel administrativo. */
    List<ServicioResponse> listarTodos();
}
