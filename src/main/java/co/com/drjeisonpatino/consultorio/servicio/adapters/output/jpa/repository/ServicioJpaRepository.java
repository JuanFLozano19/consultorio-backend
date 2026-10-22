package co.com.drjeisonpatino.consultorio.servicio.adapters.output.jpa.repository;

import co.com.drjeisonpatino.consultorio.servicio.adapters.output.jpa.entity.ServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioJpaRepository extends JpaRepository<ServicioEntity, Long> {
    List<ServicioEntity> findByActivoTrueOrderByOrdenAscIdAsc();

    List<ServicioEntity> findAllByOrderByOrdenAscIdAsc();
}
