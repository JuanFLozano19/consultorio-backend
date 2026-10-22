package co.com.drjeisonpatino.consultorio.cita.application.service;

import co.com.drjeisonpatino.consultorio.cita.application.dto.response.CitaResponse;
import co.com.drjeisonpatino.consultorio.cita.application.dto.response.PaginaResponse;
import co.com.drjeisonpatino.consultorio.cita.domain.exception.CitaNoEncontradaException;
import co.com.drjeisonpatino.consultorio.cita.domain.model.Cita;
import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.EstadoCita;
import co.com.drjeisonpatino.consultorio.cita.ports.input.ListarCitasUseCase;
import co.com.drjeisonpatino.consultorio.cita.ports.output.CitaRepositoryPort;
import co.com.drjeisonpatino.consultorio.servicio.domain.model.Servicio;
import co.com.drjeisonpatino.consultorio.servicio.ports.output.ServicioRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ListarCitasService implements ListarCitasUseCase {

    private static final int TAMANO_MAXIMO = 100;

    private final CitaRepositoryPort citaRepository;
    private final ServicioRepositoryPort servicioRepository;

    public ListarCitasService(CitaRepositoryPort citaRepository, ServicioRepositoryPort servicioRepository) {
        this.citaRepository = citaRepository;
        this.servicioRepository = servicioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PaginaResponse<CitaResponse> listar(EstadoCita estado, int pagina, int tamano) {
        int p = Math.max(pagina, 0);
        int t = Math.min(Math.max(tamano, 1), TAMANO_MAXIMO);
        Map<Long, String> nombres = nombresServicios();

        List<CitaResponse> contenido = citaRepository.listar(estado, p, t).stream()
                .map(c -> CitaMapperDto.toResponse(c, nombres.get(c.getServicioId())))
                .toList();
        long total = citaRepository.contar(estado);
        return new PaginaResponse<>(contenido, p, t, total, (int) Math.ceil((double) total / t));
    }

    @Override
    @Transactional(readOnly = true)
    public CitaResponse obtener(Long id) {
        Cita c = citaRepository.buscarPorId(id).orElseThrow(() -> new CitaNoEncontradaException(id));
        return CitaMapperDto.toResponse(c, nombresServicios().get(c.getServicioId()));
    }

    private Map<Long, String> nombresServicios() {
        return servicioRepository.listarTodosOrdenados().stream()
                .collect(Collectors.toMap(Servicio::getId, Servicio::getNombre));
    }
}
