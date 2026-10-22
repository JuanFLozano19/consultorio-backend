package co.com.drjeisonpatino.consultorio.usuario.ports.input;

import co.com.drjeisonpatino.consultorio.usuario.application.dto.response.UsuarioResponse;

public interface ObtenerPerfilUseCase {
    UsuarioResponse obtenerPerfil(String correo);
}
