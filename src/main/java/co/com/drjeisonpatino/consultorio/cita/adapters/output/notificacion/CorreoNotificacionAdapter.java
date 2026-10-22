package co.com.drjeisonpatino.consultorio.cita.adapters.output.notificacion;

import co.com.drjeisonpatino.consultorio.cita.domain.model.Cita;
import co.com.drjeisonpatino.consultorio.cita.ports.output.NotificacionCitaPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Envía un correo al consultorio cuando llega una solicitud.
 * Si NOTIFICACION_CORREO o MAIL_USERNAME no están configurados, solo deja el registro en el log.
 * Por privacidad, el correo NO incluye el documento ni el motivo de consulta: se ven en el panel.
 */
@Component
public class CorreoNotificacionAdapter implements NotificacionCitaPort {

    private static final Logger log = LoggerFactory.getLogger(CorreoNotificacionAdapter.class);
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM", Locale.forLanguageTag("es-CO"));

    private final ObjectProvider<JavaMailSender> mailSender;
    private final String destino;
    private final String remitente;

    public CorreoNotificacionAdapter(ObjectProvider<JavaMailSender> mailSender,
                                     @Value("${app.notificacion.correo-destino:}") String destino,
                                     @Value("${spring.mail.username:}") String remitente) {
        this.mailSender = mailSender;
        this.destino = destino;
        this.remitente = remitente;
    }

    @Async
    @Override
    public void notificarNuevaSolicitud(Cita cita, String servicioNombre) {
        JavaMailSender sender = mailSender.getIfAvailable();
        if (destino.isBlank() || remitente.isBlank() || sender == null) {
            log.info("Nueva solicitud de cita #{} para el {} ({})", cita.getId(), cita.getFechaPreferida(), cita.getFranja());
            return;
        }

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(remitente);
        msg.setTo(destino);
        msg.setSubject("Nueva solicitud de cita #" + cita.getId());
        msg.setText("""
                Llegó una nueva solicitud de cita desde la web.

                Paciente: %s
                Teléfono: %s
                Servicio: %s
                Fecha preferida: %s, en la %s

                Revisa el detalle y confírmala en el panel administrativo.
                """.formatted(cita.getNombrePaciente(), cita.getTelefono(),
                servicioNombre == null ? "No especificado" : servicioNombre,
                cita.getFechaPreferida().format(FECHA),
                cita.getFranja().name().equals("MANANA") ? "mañana" : "tarde"));
        try {
            sender.send(msg);
        } catch (Exception e) {
            log.warn("No se pudo enviar el correo de la cita #{}: {}", cita.getId(), e.getMessage());
        }
    }
}
