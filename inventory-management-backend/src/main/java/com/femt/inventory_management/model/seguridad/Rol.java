package com.femt.inventory_management.model.seguridad;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seguridad_rol")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(nullable = false, length = 15)
    private String nombre;
}
