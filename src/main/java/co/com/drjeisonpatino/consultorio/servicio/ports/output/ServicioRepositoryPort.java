package co.com.drjeisonpatino.consultorio.servicio.ports.output;

import co.com.drjeisonpatino.consultorio.servicio.domain.model.Servicio;

import java.util.List;
import java.util.Optional;

public interface ServicioRepositoryPort {
    List<Servicio> listarActivosOrdenados();

    List<Servicio> listarTodosOrdenados();

    Optional<Servicio> buscarPorId(Long id);

    Servicio guardar(Servicio servicio);
}
