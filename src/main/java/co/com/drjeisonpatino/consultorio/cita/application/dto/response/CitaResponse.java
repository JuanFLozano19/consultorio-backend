package co.com.drjeisonpatino.consultorio.cita.application.dto.response;

import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.EstadoCita;
import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.Franja;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/** Vista completa de la cita para el panel administrativo. */
@Getter
@AllArgsConstructor
public class CitaResponse {
    private Long id;
    private String nombrePaciente;
    private String documento;
    private String telefono;
    private String correo;
    private Long servicioId;
    private String servicioNombre;
    private LocalDate fechaPreferida;
    private Franja franja;
    private String motivo;
    private EstadoCita estado;
    private String notasInternas;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaSolicitud;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaActualizacion;
}
