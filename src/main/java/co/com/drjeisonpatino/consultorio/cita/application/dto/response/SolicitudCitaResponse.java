package co.com.drjeisonpatino.consultorio.cita.application.dto.response;

import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.EstadoCita;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** Lo que recibe el paciente al enviar el formulario: sin datos sensibles. */
@Getter
@AllArgsConstructor
public class SolicitudCitaResponse {
    private Long id;
    private EstadoCita estado;
    private String mensaje;
}
