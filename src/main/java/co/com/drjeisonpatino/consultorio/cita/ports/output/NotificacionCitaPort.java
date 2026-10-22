package co.com.drjeisonpatino.consultorio.cita.ports.output;

import co.com.drjeisonpatino.consultorio.cita.domain.model.Cita;

/** Avisa al consultorio que llegó una solicitud nueva (correo, WhatsApp, etc.). */
public interface NotificacionCitaPort {
    void notificarNuevaSolicitud(Cita cita, String servicioNombre);
}
