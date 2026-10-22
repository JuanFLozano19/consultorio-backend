package co.com.drjeisonpatino.consultorio.cita.adapters.output.jpa.mapper;

import co.com.drjeisonpatino.consultorio.cita.adapters.output.jpa.entity.CitaEntity;
import co.com.drjeisonpatino.consultorio.cita.domain.model.Cita;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper {

    public Cita toDomain(CitaEntity e) {
        if (e == null) return null;
        return new Cita(e.getId(), e.getNombrePaciente(), e.getDocumento(), e.getTelefono(), e.getCorreo(),
                e.getServicioId(), e.getFechaPreferida(), e.getFranja(), e.getMotivo(), e.getEstado(),
                e.isAceptaTratamientoDatos(), e.getNotasInternas(), e.getFechaSolicitud(), e.getFechaActualizacion());
    }

    public CitaEntity toEntity(Cita d) {
        if (d == null) return null;
        return new CitaEntity(d.getId(), d.getNombrePaciente(), d.getDocumento(), d.getTelefono(), d.getCorreo(),
                d.getServicioId(), d.getFechaPreferida(), d.getFranja(), d.getMotivo(), d.getEstado(),
                d.isAceptaTratamientoDatos(), d.getNotasInternas(), d.getFechaSolicitud(), d.getFechaActualizacion());
    }
}
