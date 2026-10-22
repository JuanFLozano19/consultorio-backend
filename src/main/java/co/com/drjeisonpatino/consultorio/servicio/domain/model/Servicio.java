package co.com.drjeisonpatino.consultorio.servicio.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Servicio {
    private Long id;
    private String nombre;
    private String descripcion;
    /** Nombre del ícono que pinta el frontend: stethoscope, pulse, heart, document. */
    private String icono;
    private int orden;
    private boolean activo;
}
