package co.com.drjeisonpatino.consultorio.cita.application.dto.request;

import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.Franja;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitarCitaRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    private String nombrePaciente;

    @NotBlank(message = "El documento es obligatorio")
    @Pattern(regexp = "^[0-9A-Za-z]{5,20}$", message = "Documento inválido (solo números y letras, sin puntos)")
    private String documento;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[0-9 ]{7,20}$", message = "Teléfono inválido")
    private String telefono;

    @Email(message = "Debe ser un correo válido")
    @Size(max = 255)
    private String correo;

    private Long servicioId;

    @NotNull(message = "La fecha preferida es obligatoria")
    private LocalDate fechaPreferida;

    @NotNull(message = "La franja es obligatoria (MANANA o TARDE)")
    private Franja franja;

    @Size(max = 500, message = "El motivo puede tener máximo 500 caracteres")
    private String motivo;

    @AssertTrue(message = "Debes aceptar el tratamiento de datos personales")
    private boolean aceptaTratamientoDatos;
}
