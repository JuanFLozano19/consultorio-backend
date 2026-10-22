package co.com.drjeisonpatino.consultorio.cita.ports.input;

import co.com.drjeisonpatino.consultorio.cita.application.dto.request.CambiarEstadoCitaRequest;
import co.com.drjeisonpatino.consultorio.cita.application.dto.response.CitaResponse;

public interface CambiarEstadoCitaUseCase {
    CitaResponse cambiarEstado(Long id, CambiarEstadoCitaRequest request);
}
