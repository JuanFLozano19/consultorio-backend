package co.com.drjeisonpatino.consultorio.cita.ports.output;

import co.com.drjeisonpatino.consultorio.cita.domain.model.Cita;
import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.EstadoCita;

import java.util.List;
import java.util.Optional;

public interface CitaRepositoryPort {
    Cita guardar(Cita cita);

    Optional<Cita> buscarPorId(Long id);

    /** Página de citas (más recientes primero), filtrando por estado si no es null. */
    List<Cita> listar(EstadoCita estado, int pagina, int tamano);

    long contar(EstadoCita estado);
}
