package co.com.drjeisonpatino.consultorio.cita;

import co.com.drjeisonpatino.consultorio.cita.domain.exception.SolicitudCitaInvalidaException;
import co.com.drjeisonpatino.consultorio.cita.domain.exception.TransicionEstadoInvalidaException;
import co.com.drjeisonpatino.consultorio.cita.domain.model.Cita;
import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.EstadoCita;
import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.Franja;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CitaTest {

    // Miércoles 21 de octubre de 2026, 10:00 a. m.
    private static final LocalDateTime AHORA = LocalDateTime.of(2026, 10, 21, 10, 0);

    private Cita solicitarPara(LocalDate fecha, boolean acepta) {
        return Cita.solicitar("  Ana   María Pérez ", "1071000000", "300 123 4567", " Ana@Correo.com ",
                null, fecha, Franja.MANANA, "Control de presión", acepta, AHORA);
    }

    @Test
    void creaSolicitudPendienteYLimpiaLosDatos() {
        Cita c = solicitarPara(LocalDate.of(2026, 10, 23), true);
        assertThat(c.getEstado()).isEqualTo(EstadoCita.PENDIENTE);
        assertThat(c.getNombrePaciente()).isEqualTo("Ana María Pérez");
        assertThat(c.getCorreo()).isEqualTo("ana@correo.com");
        assertThat(c.getFechaSolicitud()).isEqualTo(AHORA);
    }

    @Test
    void permiteElMismoDia() {
        assertThat(solicitarPara(AHORA.toLocalDate(), true).getEstado()).isEqualTo(EstadoCita.PENDIENTE);
    }

    @Test
    void rechazaFechaPasada() {
        assertThatThrownBy(() -> solicitarPara(LocalDate.of(2026, 10, 20), true))
                .isInstanceOf(SolicitudCitaInvalidaException.class).hasMessageContaining("pasado");
    }

    @Test
    void rechazaDomingo() {
        assertThatThrownBy(() -> solicitarPara(LocalDate.of(2026, 10, 25), true))
                .isInstanceOf(SolicitudCitaInvalidaException.class).hasMessageContaining("domingos");
    }

    @Test
    void rechazaMasDeNoventaDias() {
        assertThatThrownBy(() -> solicitarPara(AHORA.toLocalDate().plusDays(91), true))
                .isInstanceOf(SolicitudCitaInvalidaException.class);
    }

    @Test
    void exigeAceptarTratamientoDeDatos() {
        assertThatThrownBy(() -> solicitarPara(LocalDate.of(2026, 10, 23), false))
                .isInstanceOf(SolicitudCitaInvalidaException.class).hasMessageContaining("datos");
    }

    @Test
    void sigueElFlujoDeEstados() {
        Cita c = solicitarPara(LocalDate.of(2026, 10, 23), true);
        c.cambiarEstado(EstadoCita.CONFIRMADA, AHORA.plusHours(1));
        c.cambiarEstado(EstadoCita.ATENDIDA, AHORA.plusDays(2));
        assertThat(c.getEstado()).isEqualTo(EstadoCita.ATENDIDA);
        assertThat(c.getFechaActualizacion()).isEqualTo(AHORA.plusDays(2));
    }

    @Test
    void noPermiteAtenderSinConfirmar() {
        Cita c = solicitarPara(LocalDate.of(2026, 10, 23), true);
        assertThatThrownBy(() -> c.cambiarEstado(EstadoCita.ATENDIDA, AHORA))
                .isInstanceOf(TransicionEstadoInvalidaException.class);
    }

    @Test
    void unaCitaCanceladaNoSeReabre() {
        Cita c = solicitarPara(LocalDate.of(2026, 10, 23), true);
        c.cambiarEstado(EstadoCita.CANCELADA, AHORA);
        assertThatThrownBy(() -> c.cambiarEstado(EstadoCita.CONFIRMADA, AHORA))
                .isInstanceOf(TransicionEstadoInvalidaException.class);
    }
}
