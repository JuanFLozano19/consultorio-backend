package co.com.drjeisonpatino.consultorio.servicio.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServicioResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private String icono;
    private int orden;
    private boolean activo;
}
