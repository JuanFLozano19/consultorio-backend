package co.com.drjeisonpatino.consultorio.cita.application.service;

import co.com.drjeisonpatino.consultorio.cita.application.dto.request.CambiarEstadoCitaRequest;
import co.com.drjeisonpatino.consultorio.cita.application.dto.response.CitaResponse;
import co.com.drjeisonpatino.consultorio.cita.domain.exception.CitaNoEncontradaException;
import co.com.drjeisonpatino.consultorio.cita.domain.model.Cita;
import co.com.drjeisonpatino.consultorio.cita.ports.input.CambiarEstadoCitaUseCase;
import co.com.drjeisonpatino.consultorio.cita.ports.output.CitaRepositoryPort;
import co.com.drjeisonpatino.consultorio.servicio.ports.output.ServicioRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class CambiarEstadoCitaService implements CambiarEstadoCitaUseCase {

    private final CitaRepositoryPort citaRepository;
    private final ServicioRepositoryPort servicioRepository;
    private final Clock clock;

    public CambiarEstadoCitaService(CitaRepositoryPort citaRepository, ServicioRepositoryPort servicioRepository, Clock clock) {
        this.citaRepository = citaRepository;
        this.servicioRepository = servicioRepository;
        this.clock = clock;
    }

    @Override
    @Transactional
    public CitaResponse cambiarEstado(Long id, CambiarEstadoCitaRequest r) {
        Cita cita = citaRepository.buscarPorId(id).orElseThrow(() -> new CitaNoEncontradaException(id));
        cita.cambiarEstado(r.getEstado(), LocalDateTime.now(clock));
        if (r.getNotasInternas() != null) {
            cita.setNotasInternas(r.getNotasInternas().isBlank() ? null : r.getNotasInternas().trim());
        }
        Cita guardada = citaRepository.guardar(cita);
        String servicioNombre = guardada.getServicioId() == null ? null
                : servicioRepository.buscarPorId(guardada.getServicioId()).map(s -> s.getNombre()).orElse(null);
        return CitaMapperDto.toResponse(guardada, servicioNombre);
    }
}
