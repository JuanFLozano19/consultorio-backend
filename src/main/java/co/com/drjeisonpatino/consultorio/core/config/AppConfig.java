package co.com.drjeisonpatino.consultorio.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
@EnableAsync
public class AppConfig {

    /** Reloj en hora de Colombia; en las pruebas se reemplaza por uno fijo. */
    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.of("America/Bogota"));
    }
}
