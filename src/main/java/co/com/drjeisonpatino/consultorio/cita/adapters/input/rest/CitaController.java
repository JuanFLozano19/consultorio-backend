package co.com.drjeisonpatino.consultorio.cita.adapters.input.rest;

import co.com.drjeisonpatino.consultorio.cita.application.dto.request.CambiarEstadoCitaRequest;
import co.com.drjeisonpatino.consultorio.cita.application.dto.request.SolicitarCitaRequest;
import co.com.drjeisonpatino.consultorio.cita.application.dto.response.CitaResponse;
import co.com.drjeisonpatino.consultorio.cita.application.dto.response.PaginaResponse;
import co.com.drjeisonpatino.consultorio.cita.application.dto.response.SolicitudCitaResponse;
import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.EstadoCita;
import co.com.drjeisonpatino.consultorio.cita.ports.input.CambiarEstadoCitaUseCase;
import co.com.drjeisonpatino.consultorio.cita.ports.input.ListarCitasUseCase;
import co.com.drjeisonpatino.consultorio.cita.ports.input.SolicitarCitaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/citas")
@Tag(name = "Citas", description = "Solicitudes de cita desde la web y su gestión")
public class CitaController {

    private final SolicitarCitaUseCase solicitarCita;
    private final ListarCitasUseCase listarCitas;
    private final CambiarEstadoCitaUseCase cambiarEstado;

    public CitaController(SolicitarCitaUseCase solicitarCita, ListarCitasUseCase listarCitas,
                          CambiarEstadoCitaUseCase cambiarEstado) {
        this.solicitarCita = solicitarCita;
        this.listarCitas = listarCitas;
        this.cambiarEstado = cambiarEstado;
    }

    @Operation(summary = "Solicitar cita (público)",
            description = "Lo usa el formulario de la web. Queda en estado PENDIENTE hasta que el consultorio la confirme.")
    @PostMapping
    public ResponseEntity<SolicitudCitaResponse> solicitar(@Valid @RequestBody SolicitarCitaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitarCita.solicitar(request));
    }

    @Operation(summary = "Listar citas (admin)", description = "Más recientes primero. Filtro opcional por estado.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public PaginaResponse<CitaResponse> listar(@RequestParam(required = false) EstadoCita estado,
                                               @RequestParam(defaultValue = "0") int pagina,
                                               @RequestParam(defaultValue = "20") int tamano) {
        return listarCitas.listar(estado, pagina, tamano);
    }

    @Operation(summary = "Ver una cita (admin)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public CitaResponse obtener(@PathVariable Long id) {
        return listarCitas.obtener(id);
    }

    @Operation(summary = "Cambiar estado de una cita (admin)",
            description = "PENDIENTE → CONFIRMADA o CANCELADA; CONFIRMADA → ATENDIDA o CANCELADA.")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/estado")
    public CitaResponse cambiarEstado(@PathVariable Long id, @Valid @RequestBody CambiarEstadoCitaRequest request) {
        return cambiarEstado.cambiarEstado(id, request);
    }
}
