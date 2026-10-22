package co.com.drjeisonpatino.consultorio.servicio.adapters.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "servicios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false, length = 40)
    private String icono;

    @Column(nullable = false)
    private int orden;

    @Column(nullable = false)
    private boolean activo;
}
