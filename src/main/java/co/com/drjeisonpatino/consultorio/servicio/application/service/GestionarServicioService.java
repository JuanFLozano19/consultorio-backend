package co.com.drjeisonpatino.consultorio.servicio.application.service;

import co.com.drjeisonpatino.consultorio.servicio.application.dto.request.ServicioRequest;
import co.com.drjeisonpatino.consultorio.servicio.application.dto.response.ServicioResponse;
import co.com.drjeisonpatino.consultorio.servicio.domain.exception.ServicioNoEncontradoException;
import co.com.drjeisonpatino.consultorio.servicio.domain.model.Servicio;
import co.com.drjeisonpatino.consultorio.servicio.ports.input.GestionarServicioUseCase;
import co.com.drjeisonpatino.consultorio.servicio.ports.output.ServicioRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class GestionarServicioService implements GestionarServicioUseCase {

    private final ServicioRepositoryPort repository;

    public GestionarServicioService(ServicioRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public ServicioResponse crear(ServicioRequest r) {
        Servicio nuevo = new Servicio(null, r.getNombre().trim(), r.getDescripcion().trim(), r.getIcono(), r.getOrden(), r.isActivo());
        return ListarServiciosService.toResponse(repository.guardar(nuevo));
    }

    @Override
    public ServicioResponse actualizar(Long id, ServicioRequest r) {
        Servicio s = repository.buscarPorId(id).orElseThrow(() -> new ServicioNoEncontradoException(id));
        s.setNombre(r.getNombre().trim());
        s.setDescripcion(r.getDescripcion().trim());
        s.setIcono(r.getIcono());
        s.setOrden(r.getOrden());
        s.setActivo(r.isActivo());
        return ListarServiciosService.toResponse(repository.guardar(s));
    }
}
