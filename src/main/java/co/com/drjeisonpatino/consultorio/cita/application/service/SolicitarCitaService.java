package co.com.drjeisonpatino.consultorio.cita.application.service;

import co.com.drjeisonpatino.consultorio.cita.application.dto.request.SolicitarCitaRequest;
import co.com.drjeisonpatino.consultorio.cita.application.dto.response.SolicitudCitaResponse;
import co.com.drjeisonpatino.consultorio.cita.domain.exception.SolicitudCitaInvalidaException;
import co.com.drjeisonpatino.consultorio.cita.domain.model.Cita;
import co.com.drjeisonpatino.consultorio.cita.ports.input.SolicitarCitaUseCase;
import co.com.drjeisonpatino.consultorio.cita.ports.output.CitaRepositoryPort;
import co.com.drjeisonpatino.consultorio.cita.ports.output.NotificacionCitaPort;
import co.com.drjeisonpatino.consultorio.servicio.domain.model.Servicio;
import co.com.drjeisonpatino.consultorio.servicio.ports.output.ServicioRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class SolicitarCitaService implements SolicitarCitaUseCase {

    private static final Logger log = LoggerFactory.getLogger(SolicitarCitaService.class);

    private final CitaRepositoryPort citaRepository;
    private final ServicioRepositoryPort servicioRepository;
    private final NotificacionCitaPort notificacion;
    private final Clock clock;

    public SolicitarCitaService(CitaRepositoryPort citaRepository, ServicioRepositoryPort servicioRepository,
                                NotificacionCitaPort notificacion, Clock clock) {
        this.citaRepository = citaRepository;
        this.servicioRepository = servicioRepository;
        this.notificacion = notificacion;
        this.clock = clock;
    }

    @Override
    @Transactional
    public SolicitudCitaResponse solicitar(SolicitarCitaRequest r) {
        String servicioNombre = null;
        if (r.getServicioId() != null) {
            Servicio servicio = servicioRepository.buscarPorId(r.getServicioId())
                    .filter(Servicio::isActivo)
                    .orElseThrow(() -> new SolicitudCitaInvalidaException("El servicio seleccionado no está disponible"));
            servicioNombre = servicio.getNombre();
        }

        Cita cita = Cita.solicitar(r.getNombrePaciente(), r.getDocumento(), r.getTelefono(), r.getCorreo(),
                r.getServicioId(), r.getFechaPreferida(), r.getFranja(), r.getMotivo(),
                r.isAceptaTratamientoDatos(), LocalDateTime.now(clock));

        Cita guardada = citaRepository.guardar(cita);

        try {
            notificacion.notificarNuevaSolicitud(guardada, servicioNombre);
        } catch (Exception e) {
            // Si falla el aviso, la solicitud igual queda guardada
            log.warn("No se pudo enviar el aviso de la cita {}: {}", guardada.getId(), e.getMessage());
        }

        return new SolicitudCitaResponse(guardada.getId(), guardada.getEstado(),
                "Recibimos tu solicitud. Te contactaremos para confirmar la hora de tu cita.");
    }
}
