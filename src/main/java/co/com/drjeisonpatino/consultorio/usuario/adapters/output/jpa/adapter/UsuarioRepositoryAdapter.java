package co.com.drjeisonpatino.consultorio.usuario.adapters.output.jpa.adapter;

import co.com.drjeisonpatino.consultorio.usuario.adapters.output.jpa.mapper.UsuarioMapper;
import co.com.drjeisonpatino.consultorio.usuario.adapters.output.jpa.repository.UsuarioJpaRepository;
import co.com.drjeisonpatino.consultorio.usuario.domain.model.Usuario;
import co.com.drjeisonpatino.consultorio.usuario.ports.output.UsuarioRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository repository;
    private final UsuarioMapper mapper;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository repository, UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return repository.findByCorreo(correo).map(mapper::toDomain);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return repository.existsByCorreo(correo);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return mapper.toDomain(repository.save(mapper.toEntity(usuario)));
    }
}
