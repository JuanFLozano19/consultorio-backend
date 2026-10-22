package co.com.drjeisonpatino.consultorio.usuario.adapters.input.startup;

import co.com.drjeisonpatino.consultorio.usuario.domain.model.Rol;
import co.com.drjeisonpatino.consultorio.usuario.domain.model.Usuario;
import co.com.drjeisonpatino.consultorio.usuario.ports.output.UsuarioRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Crea el usuario administrador la primera vez que arranca la API,
 * con ADMIN_CORREO y ADMIN_PASSWORD del entorno. Si ya existe, no hace nada.
 */
@Component
public class AdminInicialLoader implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminInicialLoader.class);

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final String nombre;
    private final String correo;
    private final String password;

    public AdminInicialLoader(UsuarioRepositoryPort usuarioRepository,
                              PasswordEncoder passwordEncoder,
                              @Value("${app.admin.nombre}") String nombre,
                              @Value("${app.admin.correo}") String correo,
                              @Value("${app.admin.password}") String password) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.nombre = nombre;
        this.correo = correo == null ? "" : correo.trim().toLowerCase();
        this.password = password;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (correo.isBlank() || password == null || password.isBlank()) {
            log.warn("ADMIN_CORREO / ADMIN_PASSWORD no configurados: no se crea administrador inicial");
            return;
        }
        if (usuarioRepository.existePorCorreo(correo)) {
            return;
        }
        if (password.length() < 10) {
            log.warn("ADMIN_PASSWORD debe tener al menos 10 caracteres: no se crea administrador inicial");
            return;
        }
        usuarioRepository.guardar(new Usuario(null, nombre, correo, passwordEncoder.encode(password), Rol.ADMIN, LocalDateTime.now()));
        log.info("Administrador inicial creado: {}", correo);
    }
}
