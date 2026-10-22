package co.com.drjeisonpatino.consultorio.usuario.ports.input;

import co.com.drjeisonpatino.consultorio.usuario.application.dto.request.LoginRequest;
import co.com.drjeisonpatino.consultorio.usuario.application.dto.response.AuthResponse;

public interface LoginUseCase {
    AuthResponse login(LoginRequest request);
}
