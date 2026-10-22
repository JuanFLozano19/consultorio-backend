package co.com.drjeisonpatino.consultorio.servicio.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicioRequest {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "Máximo 120 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "Máximo 500 caracteres")
    private String descripcion;

    @Pattern(regexp = "stethoscope|pulse|heart|document", message = "Ícono no válido")
    private String icono = "stethoscope";

    @Min(value = 0, message = "El orden no puede ser negativo")
    private int orden;

    private boolean activo = true;
}
