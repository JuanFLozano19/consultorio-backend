package co.com.drjeisonpatino.consultorio.servicio.adapters.output.jpa.adapter;

import co.com.drjeisonpatino.consultorio.servicio.adapters.output.jpa.mapper.ServicioMapper;
import co.com.drjeisonpatino.consultorio.servicio.adapters.output.jpa.repository.ServicioJpaRepository;
import co.com.drjeisonpatino.consultorio.servicio.domain.model.Servicio;
import co.com.drjeisonpatino.consultorio.servicio.ports.output.ServicioRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ServicioRepositoryAdapter implements ServicioRepositoryPort {

    private final ServicioJpaRepository repository;
    private final ServicioMapper mapper;

    public ServicioRepositoryAdapter(ServicioJpaRepository repository, ServicioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Servicio> listarActivosOrdenados() {
        return repository.findByActivoTrueOrderByOrdenAscIdAsc().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Servicio> listarTodosOrdenados() {
        return repository.findAllByOrderByOrdenAscIdAsc().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Servicio> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Servicio guardar(Servicio servicio) {
        return mapper.toDomain(repository.save(mapper.toEntity(servicio)));
    }
}
