package co.com.drjeisonpatino.consultorio.usuario.adapters.input.rest;

import co.com.drjeisonpatino.consultorio.usuario.application.dto.request.LoginRequest;
import co.com.drjeisonpatino.consultorio.usuario.application.dto.response.AuthResponse;
import co.com.drjeisonpatino.consultorio.usuario.application.dto.response.UsuarioResponse;
import co.com.drjeisonpatino.consultorio.usuario.ports.input.LoginUseCase;
import co.com.drjeisonpatino.consultorio.usuario.ports.input.ObtenerPerfilUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Inicio de sesión del panel administrativo")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final ObtenerPerfilUseCase obtenerPerfilUseCase;

    public AuthController(LoginUseCase loginUseCase, ObtenerPerfilUseCase obtenerPerfilUseCase) {
        this.loginUseCase = loginUseCase;
        this.obtenerPerfilUseCase = obtenerPerfilUseCase;
    }

    @Operation(summary = "Iniciar sesión", description = "Autentica al doctor o la secretaria y devuelve un JWT.")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(loginUseCase.login(request));
    }

    @Operation(summary = "Obtener perfil", description = "Devuelve el usuario autenticado. Requiere JWT.")
    @GetMapping("/perfil")
    public ResponseEntity<UsuarioResponse> perfil(Authentication authentication) {
        return ResponseEntity.ok(obtenerPerfilUseCase.obtenerPerfil(authentication.getName()));
    }
}
