package co.com.drjeisonpatino.consultorio.cita.ports.input;

import co.com.drjeisonpatino.consultorio.cita.application.dto.request.SolicitarCitaRequest;
import co.com.drjeisonpatino.consultorio.cita.application.dto.response.SolicitudCitaResponse;

public interface SolicitarCitaUseCase {
    SolicitudCitaResponse solicitar(SolicitarCitaRequest request);
}
