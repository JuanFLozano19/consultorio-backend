package co.com.drjeisonpatino.consultorio.usuario.ports.output;

import co.com.drjeisonpatino.consultorio.usuario.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryPort {
    Optional<Usuario> buscarPorCorreo(String correo);

    boolean existePorCorreo(String correo);

    Usuario guardar(Usuario usuario);
}
