package co.com.drjeisonpatino.consultorio.cita.application.dto.request;

import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.EstadoCita;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CambiarEstadoCitaRequest {
    @NotNull(message = "El estado es obligatorio")
    private EstadoCita estado;

    @Size(max = 1000, message = "Las notas pueden tener máximo 1000 caracteres")
    private String notasInternas;
}
