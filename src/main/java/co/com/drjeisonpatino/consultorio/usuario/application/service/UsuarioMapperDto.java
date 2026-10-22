package co.com.drjeisonpatino.consultorio.usuario.application.service;

import co.com.drjeisonpatino.consultorio.usuario.application.dto.response.UsuarioResponse;
import co.com.drjeisonpatino.consultorio.usuario.domain.model.Usuario;

final class UsuarioMapperDto {
    private UsuarioMapperDto() {
    }

    static UsuarioResponse toResponse(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNombre(), u.getCorreo(), u.getRol());
    }
}
