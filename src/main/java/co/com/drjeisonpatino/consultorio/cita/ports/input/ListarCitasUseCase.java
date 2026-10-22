package co.com.drjeisonpatino.consultorio.cita.ports.input;

import co.com.drjeisonpatino.consultorio.cita.application.dto.response.CitaResponse;
import co.com.drjeisonpatino.consultorio.cita.application.dto.response.PaginaResponse;
import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.EstadoCita;

public interface ListarCitasUseCase {
    PaginaResponse<CitaResponse> listar(EstadoCita estado, int pagina, int tamano);

    CitaResponse obtener(Long id);
}
