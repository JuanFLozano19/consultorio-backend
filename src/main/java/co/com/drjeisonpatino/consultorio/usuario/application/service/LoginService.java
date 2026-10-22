package co.com.drjeisonpatino.consultorio.usuario.application.service;

import co.com.drjeisonpatino.consultorio.core.security.service.JwtService;
import co.com.drjeisonpatino.consultorio.usuario.application.dto.request.LoginRequest;
import co.com.drjeisonpatino.consultorio.usuario.application.dto.response.AuthResponse;
import co.com.drjeisonpatino.consultorio.usuario.domain.exception.CredencialesInvalidasException;
import co.com.drjeisonpatino.consultorio.usuario.domain.model.Usuario;
import co.com.drjeisonpatino.consultorio.usuario.ports.input.LoginUseCase;
import co.com.drjeisonpatino.consultorio.usuario.ports.output.UsuarioRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginService(UsuarioRepositoryPort usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.buscarPorCorreo(request.getCorreo().trim().toLowerCase())
                .orElseThrow(() -> new CredencialesInvalidasException("Correo o contraseña incorrectos"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new CredencialesInvalidasException("Correo o contraseña incorrectos");
        }

        String token = jwtService.generarToken(usuario.getCorreo(), usuario.getRol());
        return new AuthResponse(UsuarioMapperDto.toResponse(usuario), token, jwtService.getExpiracionMs());
    }
}
