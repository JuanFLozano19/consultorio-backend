package co.com.drjeisonpatino.consultorio.cita.adapters.output.jpa.repository;

import co.com.drjeisonpatino.consultorio.cita.adapters.output.jpa.entity.CitaEntity;
import co.com.drjeisonpatino.consultorio.cita.domain.valueobject.EstadoCita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaJpaRepository extends JpaRepository<CitaEntity, Long> {
    Page<CitaEntity> findByEstado(EstadoCita estado, Pageable pageable);

    long countByEstado(EstadoCita estado);
}
