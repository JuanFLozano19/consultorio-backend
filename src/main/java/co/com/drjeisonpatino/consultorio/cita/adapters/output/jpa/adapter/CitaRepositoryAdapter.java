package co.com.drjeisonpatino.consultorio.cita.adapters.output.jpa.adapter;

import co.com.drjeisonpatino.consultorio.cita.adapters.output.jpa.entity.CitaEntity;
import co.com.drjeisonpatino.consultorio.cita.adapters.output.jpa.mapper.CitaMapper;
import co.com.drjeisonpatino.consultorio.cita.adapters.output.jpa.repository.CitaJpaRepository;
import co.com.drjeisonpatino.consultorio.cita.domain.model.Cita;
import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.EstadoCita;
import co.com.drjeisonpatino.consultorio.cita.ports.output.CitaRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CitaRepositoryAdapter implements CitaRepositoryPort {

    private final CitaJpaRepository repository;
    private final CitaMapper mapper;

    public CitaRepositoryAdapter(CitaJpaRepository repository, CitaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Cita guardar(Cita cita) {
        return mapper.toDomain(repository.save(mapper.toEntity(cita)));
    }

    @Override
    public Optional<Cita> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Cita> listar(EstadoCita estado, int pagina, int tamano) {
        PageRequest page = PageRequest.of(pagina, tamano, Sort.by(Sort.Direction.DESC, "fechaSolicitud"));
        Page<CitaEntity> resultado = estado == null ? repository.findAll(page) : repository.findByEstado(estado, page);
        return resultado.map(mapper::toDomain).getContent();
    }

    @Override
    public long contar(EstadoCita estado) {
        return estado == null ? repository.count() : repository.countByEstado(estado);
    }
}
