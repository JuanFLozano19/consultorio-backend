package co.com.drjeisonpatino.consultorio.core.exception;

import co.com.drjeisonpatino.consultorio.cita.domain.exception.CitaNoEncontradaException;
import co.com.drjeisonpatino.consultorio.cita.domain.exception.SolicitudCitaInvalidaException;
import co.com.drjeisonpatino.consultorio.cita.domain.exception.TransicionEstadoInvalidaException;
import co.com.drjeisonpatino.consultorio.servicio.domain.exception.ServicioNoEncontradoException;
import co.com.drjeisonpatino.consultorio.usuario.domain.exception.CredencialesInvalidasException;
import co.com.drjeisonpatino.consultorio.usuario.domain.exception.UsuarioNoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ============================================
    // Módulo cita
    // ============================================

    @ExceptionHandler(CitaNoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handleCitaNoEncontrada(CitaNoEncontradaException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    @ExceptionHandler(SolicitudCitaInvalidaException.class)
    public ResponseEntity<ErrorResponse> handleSolicitudInvalida(SolicitudCitaInvalidaException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req);
    }

    @ExceptionHandler(TransicionEstadoInvalidaException.class)
    public ResponseEntity<ErrorResponse> handleTransicion(TransicionEstadoInvalidaException ex, HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), req);
    }

    // ============================================
    // Módulo servicio
    // ============================================

    @ExceptionHandler(ServicioNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleServicioNoEncontrado(ServicioNoEncontradoException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    // ============================================
    // Módulo usuario
    // ============================================

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<ErrorResponse> handleCredenciales(CredencialesInvalidasException ex, HttpServletRequest req) {
        return build(HttpStatus.UNAUTHORIZED, ex.getMessage(), req);
    }

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleUsuario(UsuarioNoEncontradoException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    // ============================================
    // Validación y peticiones mal formadas
    // ============================================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return build(HttpStatus.BAD_REQUEST, mensaje, req);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleMalFormada(Exception ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, "La petición tiene un formato inválido", req);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAcceso(AccessDeniedException ex, HttpServletRequest req) {
        return build(HttpStatus.FORBIDDEN, "No tienes permiso para esta acción", req);
    }

    // ============================================
    // Fallback: no exponemos detalles internos al cliente
    // ============================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, HttpServletRequest req) {
        log.error("Error no controlado en {}", req.getRequestURI(), ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado", req);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String mensaje, HttpServletRequest req) {
        return ResponseEntity.status(status).body(new ErrorResponse(
                LocalDateTime.now(), status.value(), status.getReasonPhrase(), mensaje, req.getRequestURI()));
    }
}
