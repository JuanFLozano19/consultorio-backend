package co.com.drjeisonpatino.consultorio.core.security.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Limita cuántas solicitudes de cita puede enviar una misma IP por hora
 * (evita spam en el formulario público). Solo aplica a POST /api/citas y al login.
 */
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    private final int limitePorHora;

    public RateLimitFilter(@Value("${app.citas.limite-por-hora:5}") int limitePorHora) {
        this.limitePorHora = limitePorHora;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (!"POST".equalsIgnoreCase(request.getMethod())) return true;
        String uri = request.getRequestURI();
        return !(uri.equals("/api/citas") || uri.equals("/api/auth/login"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String clave = obtenerIp(request) + "|" + request.getRequestURI();
        Bucket bucket = buckets.computeIfAbsent(clave, k -> Bucket.builder()
                .addLimit(Bandwidth.builder().capacity(limitePorHora).refillGreedy(limitePorHora, Duration.ofHours(1)).build())
                .build());

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
            return;
        }
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"status\":429,\"error\":\"Too Many Requests\","
                + "\"message\":\"Has enviado demasiadas solicitudes. Intenta más tarde o escríbenos por WhatsApp.\"}");
    }

    private String obtenerIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
