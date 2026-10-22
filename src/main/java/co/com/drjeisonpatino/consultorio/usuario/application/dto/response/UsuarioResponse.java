package co.com.drjeisonpatino.consultorio.usuario.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String correo;
    private String rol;
}
