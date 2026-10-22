package co.com.drjeisonpatino.consultorio.servicio.application.service;

import co.com.drjeisonpatino.consultorio.servicio.application.dto.response.ServicioResponse;
import co.com.drjeisonpatino.consultorio.servicio.domain.model.Servicio;
import co.com.drjeisonpatino.consultorio.servicio.ports.input.ListarServiciosUseCase;
import co.com.drjeisonpatino.consultorio.servicio.ports.output.ServicioRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarServiciosService implements ListarServiciosUseCase {

    private final ServicioRepositoryPort repository;

    public ListarServiciosService(ServicioRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<ServicioResponse> listarActivos() {
        return repository.listarActivosOrdenados().stream().map(ListarServiciosService::toResponse).toList();
    }

    @Override
    public List<ServicioResponse> listarTodos() {
        return repository.listarTodosOrdenados().stream().map(ListarServiciosService::toResponse).toList();
    }

    static ServicioResponse toResponse(Servicio s) {
        return new ServicioResponse(s.getId(), s.getNombre(), s.getDescripcion(), s.getIcono(), s.getOrden(), s.isActivo());
    }
}
