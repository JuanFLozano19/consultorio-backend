package co.com.drjeisonpatino.consultorio.servicio.ports.input;

import co.com.drjeisonpatino.consultorio.servicio.application.dto.request.ServicioRequest;
import co.com.drjeisonpatino.consultorio.servicio.application.dto.response.ServicioResponse;

public interface GestionarServicioUseCase {
    ServicioResponse crear(ServicioRequest request);

    ServicioResponse actualizar(Long id, ServicioRequest request);
}
