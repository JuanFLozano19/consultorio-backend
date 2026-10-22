package co.com.drjeisonpatino.consultorio.cita.application.service;

import co.com.drjeisonpatino.consultorio.cita.application.dto.response.CitaResponse;
import co.com.drjeisonpatino.consultorio.cita.domain.model.Cita;

final class CitaMapperDto {
    private CitaMapperDto() {
    }

    static CitaResponse toResponse(Cita c, String servicioNombre) {
        return new CitaResponse(c.getId(), c.getNombrePaciente(), c.getDocumento(), c.getTelefono(), c.getCorreo(),
                c.getServicioId(), servicioNombre, c.getFechaPreferida(), c.getFranja(), c.getMotivo(), c.getEstado(),
                c.getNotasInternas(), c.getFechaSolicitud(), c.getFechaActualizacion());
    }
}
