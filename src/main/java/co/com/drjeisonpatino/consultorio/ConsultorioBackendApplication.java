package co.com.drjeisonpatino.consultorio;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ConsultorioBackendApplication {

    public static void main(String[] args) {
        // Zona horaria de Colombia para fechas de citas
        TimeZone.setDefault(TimeZone.getTimeZone("America/Bogota"));

        // Cargar variables del .env ANTES de que Spring arranque (en la nube vienen del entorno)
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMissing()
                .load();
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );

        SpringApplication.run(ConsultorioBackendApplication.class, args);
    }
}
