package co.com.drjeisonpatino.consultorio.usuario.adapters.output.jpa.mapper;

import co.com.drjeisonpatino.consultorio.usuario.adapters.output.jpa.entity.UsuarioEntity;
import co.com.drjeisonpatino.consultorio.usuario.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toDomain(UsuarioEntity e) {
        if (e == null) return null;
        return new Usuario(e.getId(), e.getNombre(), e.getCorreo(), e.getPassword(), e.getRol(), e.getFechaRegistro());
    }

    public UsuarioEntity toEntity(Usuario d) {
        if (d == null) return null;
        return new UsuarioEntity(d.getId(), d.getNombre(), d.getCorreo(), d.getPassword(), d.getRol(), d.getFechaRegistro());
    }
}
