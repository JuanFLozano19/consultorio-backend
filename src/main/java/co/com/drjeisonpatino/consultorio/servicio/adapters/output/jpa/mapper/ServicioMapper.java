package co.com.drjeisonpatino.consultorio.servicio.adapters.output.jpa.mapper;

import co.com.drjeisonpatino.consultorio.servicio.adapters.output.jpa.entity.ServicioEntity;
import co.com.drjeisonpatino.consultorio.servicio.domain.model.Servicio;
import org.springframework.stereotype.Component;

@Component
public class ServicioMapper {

    public Servicio toDomain(ServicioEntity e) {
        if (e == null) return null;
        return new Servicio(e.getId(), e.getNombre(), e.getDescripcion(), e.getIcono(), e.getOrden(), e.isActivo());
    }

    public ServicioEntity toEntity(Servicio d) {
        if (d == null) return null;
        return new ServicioEntity(d.getId(), d.getNombre(), d.getDescripcion(), d.getIcono(), d.getOrden(), d.isActivo());
    }
}
