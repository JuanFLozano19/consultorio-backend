package co.com.drjeisonpatino.consultorio.servicio.adapters.input.rest;

import co.com.drjeisonpatino.consultorio.servicio.application.dto.request.ServicioRequest;
import co.com.drjeisonpatino.consultorio.servicio.application.dto.response.ServicioResponse;
import co.com.drjeisonpatino.consultorio.servicio.ports.input.GestionarServicioUseCase;
import co.com.drjeisonpatino.consultorio.servicio.ports.input.ListarServiciosUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@Tag(name = "Servicios", description = "Servicios del consultorio que se muestran en la web")
public class ServicioController {

    private final ListarServiciosUseCase listarServicios;
    private final GestionarServicioUseCase gestionarServicio;

    public ServicioController(ListarServiciosUseCase listarServicios, GestionarServicioUseCase gestionarServicio) {
        this.listarServicios = listarServicios;
        this.gestionarServicio = gestionarServicio;
    }

    @Operation(summary = "Listar servicios activos (público)")
    @GetMapping
    public List<ServicioResponse> listarActivos() {
        return listarServicios.listarActivos();
    }

    @Operation(summary = "Listar todos los servicios (admin)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/todos")
    public List<ServicioResponse> listarTodos() {
        return listarServicios.listarTodos();
    }

    @Operation(summary = "Crear servicio (admin)")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ServicioResponse> crear(@Valid @RequestBody ServicioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gestionarServicio.crear(request));
    }

    @Operation(summary = "Actualizar o desactivar servicio (admin)")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ServicioResponse actualizar(@PathVariable Long id, @Valid @RequestBody ServicioRequest request) {
        return gestionarServicio.actualizar(id, request);
    }
}
