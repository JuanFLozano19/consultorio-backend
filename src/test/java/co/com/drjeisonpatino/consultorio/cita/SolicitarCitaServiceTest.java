package co.com.drjeisonpatino.consultorio.cita;

import co.com.drjeisonpatino.consultorio.cita.application.dto.request.SolicitarCitaRequest;
import co.com.drjeisonpatino.consultorio.cita.application.dto.response.SolicitudCitaResponse;
import co.com.drjeisonpatino.consultorio.cita.application.service.SolicitarCitaService;
import co.com.drjeisonpatino.consultorio.cita.domain.exception.SolicitudCitaInvalidaException;
import co.com.drjeisonpatino.consultorio.cita.domain.model.Cita;
import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.EstadoCita;
import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.Franja;
import co.com.drjeisonpatino.consultorio.cita.ports.output.CitaRepositoryPort;
import co.com.drjeisonpatino.consultorio.cita.ports.output.NotificacionCitaPort;
import co.com.drjeisonpatino.consultorio.servicio.domain.model.Servicio;
import co.com.drjeisonpatino.consultorio.servicio.ports.output.ServicioRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SolicitarCitaServiceTest {

    private CitaRepositoryPort citas;
    private ServicioRepositoryPort servicios;
    private NotificacionCitaPort notificacion;
    private SolicitarCitaService service;

    @BeforeEach
    void setUp() {
        citas = mock(CitaRepositoryPort.class);
        servicios = mock(ServicioRepositoryPort.class);
        notificacion = mock(NotificacionCitaPort.class);
        Clock clock = Clock.fixed(Instant.parse("2026-10-21T15:00:00Z"), ZoneId.of("America/Bogota"));
        service = new SolicitarCitaService(citas, servicios, notificacion, clock);
        when(citas.guardar(any())).thenAnswer(inv -> {
            Cita c = inv.getArgument(0);
            c.setId(7L);
            return c;
        });
    }

    private SolicitarCitaRequest request(Long servicioId) {
        return new SolicitarCitaRequest("Ana Pérez", "1071000000", "3001234567", null, servicioId,
                LocalDate.of(2026, 10, 23), Franja.TARDE, null, true);
    }

    @Test
    void guardaLaSolicitudYAvisaAlConsultorio() {
        when(servicios.buscarPorId(2L)).thenReturn(Optional.of(new Servicio(2L, "Asesoría en actividad física", "", "pulse", 2, true)));

        SolicitudCitaResponse r = service.solicitar(request(2L));

        assertThat(r.getId()).isEqualTo(7L);
        assertThat(r.getEstado()).isEqualTo(EstadoCita.PENDIENTE);
        verify(notificacion).notificarNuevaSolicitud(any(Cita.class), eq("Asesoría en actividad física"));
    }

    @Test
    void rechazaUnServicioInactivo() {
        when(servicios.buscarPorId(9L)).thenReturn(Optional.of(new Servicio(9L, "Viejo", "", "heart", 9, false)));

        assertThatThrownBy(() -> service.solicitar(request(9L))).isInstanceOf(SolicitudCitaInvalidaException.class);
        verify(citas, never()).guardar(any());
    }

    @Test
    void siFallaElAvisoLaSolicitudIgualQueda() {
        doThrow(new RuntimeException("SMTP caído")).when(notificacion).notificarNuevaSolicitud(any(), any());

        SolicitudCitaResponse r = service.solicitar(request(null));

        assertThat(r.getId()).isEqualTo(7L);
        verify(citas).guardar(any());
    }
}
