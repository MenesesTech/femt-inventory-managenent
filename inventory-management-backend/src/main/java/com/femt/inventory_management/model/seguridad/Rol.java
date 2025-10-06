package com.femt.inventory_management.model.seguridad;

import com.femt.inventory_management.model.seguridad.enums.RolUsuarioEnum;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "nombre",nullable = false, length = 15)
    private RolUsuarioEnum rolUsuario;
}
