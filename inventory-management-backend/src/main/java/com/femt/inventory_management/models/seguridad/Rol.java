package com.femt.inventory_management.models.seguridad;

import com.femt.inventory_management.models.seguridad.enums.RolUsuarioEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "seguridad_rol")
@NoArgsConstructor
@RequiredArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nombre",nullable = false, length = 15)
    private RolUsuarioEnum rolUsuario;
}
