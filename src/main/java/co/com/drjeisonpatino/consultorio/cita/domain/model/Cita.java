package co.com.drjeisonpatino.consultorio.cita.domain.model;

import co.com.drjeisonpatino.consultorio.cita.domain.exception.SolicitudCitaInvalidaException;
import co.com.drjeisonpatino.consultorio.cita.domain.exception.TransicionEstadoInvalidaException;
import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.EstadoCita;
import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.Franja;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cita {

    /** Hasta cuántos días hacia adelante se puede pedir una cita desde la web. */
    public static final int DIAS_MAXIMOS_ANTICIPACION = 90;

    private Long id;
    private String nombrePaciente;
    private String documento;
    private String telefono;
    private String correo;
    private Long servicioId;
    private LocalDate fechaPreferida;
    private Franja franja;
    private String motivo;
    private EstadoCita estado;
    private boolean aceptaTratamientoDatos;
    private String notasInternas;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaActualizacion;

    /**
     * Crea una solicitud nueva aplicando las reglas del consultorio.
     *
     * @param hoy fecha actual (se recibe para poder probar las reglas)
     */
    public static Cita solicitar(String nombrePaciente, String documento, String telefono, String correo,
                                 Long servicioId, LocalDate fechaPreferida, Franja franja, String motivo,
                                 boolean aceptaTratamientoDatos, LocalDateTime ahora) {
        LocalDate hoy = ahora.toLocalDate();
        if (!aceptaTratamientoDatos) {
            throw new SolicitudCitaInvalidaException("Debes aceptar el tratamiento de datos personales para solicitar la cita");
        }
        if (fechaPreferida.isBefore(hoy)) {
            throw new SolicitudCitaInvalidaException("La fecha preferida no puede estar en el pasado");
        }
        if (fechaPreferida.isAfter(hoy.plusDays(DIAS_MAXIMOS_ANTICIPACION))) {
            throw new SolicitudCitaInvalidaException("Solo se pueden solicitar citas con máximo " + DIAS_MAXIMOS_ANTICIPACION + " días de anticipación");
        }
        if (fechaPreferida.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new SolicitudCitaInvalidaException("El consultorio no atiende los domingos");
        }

        return new Cita(null, limpiar(nombrePaciente), limpiar(documento), limpiar(telefono),
                vacioANull(correo == null ? null : correo.trim().toLowerCase()), servicioId, fechaPreferida, franja,
                vacioANull(limpiar(motivo)), EstadoCita.PENDIENTE, true, null, ahora, ahora);
    }

    /** Cambia el estado respetando el flujo PENDIENTE → CONFIRMADA → ATENDIDA (o CANCELADA). */
    public void cambiarEstado(EstadoCita nuevo, LocalDateTime ahora) {
        if (!estado.siguientesPermitidos().contains(nuevo)) {
            throw new TransicionEstadoInvalidaException(estado, nuevo);
        }
        this.estado = nuevo;
        this.fechaActualizacion = ahora;
    }

    private static String limpiar(String valor) {
        return valor == null ? null : valor.trim().replaceAll("\\s+", " ");
    }

    private static String vacioANull(String valor) {
        return valor == null || valor.isBlank() ? null : valor;
    }
}
