package co.com.drjeisonpatino.consultorio.usuario.application.service;

import co.com.drjeisonpatino.consultorio.usuario.application.dto.response.UsuarioResponse;
import co.com.drjeisonpatino.consultorio.usuario.domain.exception.UsuarioNoEncontradoException;
import co.com.drjeisonpatino.consultorio.usuario.ports.input.ObtenerPerfilUseCase;
import co.com.drjeisonpatino.consultorio.usuario.ports.output.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class ObtenerPerfilService implements ObtenerPerfilUseCase {

    private final UsuarioRepositoryPort usuarioRepository;

    public ObtenerPerfilService(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioResponse obtenerPerfil(String correo) {
        return usuarioRepository.buscarPorCorreo(correo)
                .map(UsuarioMapperDto::toResponse)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));
    }
}
