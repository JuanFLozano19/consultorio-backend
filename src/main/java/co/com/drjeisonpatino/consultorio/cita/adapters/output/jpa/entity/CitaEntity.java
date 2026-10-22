package co.com.drjeisonpatino.consultorio.cita.adapters.output.jpa.entity;

import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.EstadoCita;
import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.Franja;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CitaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_paciente", nullable = false, length = 150)
    private String nombrePaciente;

    @Column(nullable = false, length = 20)
    private String documento;

    @Column(nullable = false, length = 20)
    private String telefono;

    private String correo;

    @Column(name = "servicio_id")
    private Long servicioId;

    @Column(name = "fecha_preferida", nullable = false)
    private LocalDate fechaPreferida;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Franja franja;

    @Column(length = 500)
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private EstadoCita estado;

    @Column(name = "acepta_tratamiento_datos", nullable = false)
    private boolean aceptaTratamientoDatos;

    @Column(name = "notas_internas", length = 1000)
    private String notasInternas;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;
}
