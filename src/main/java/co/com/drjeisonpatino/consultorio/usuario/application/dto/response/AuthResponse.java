package co.com.drjeisonpatino.consultorio.usuario.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private UsuarioResponse usuario;
    private String token;
    private long expiraEnMs;
}
