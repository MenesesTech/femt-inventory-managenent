package com.femt.inventory_management.model.seguridad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name="seguridad_usuario",
        indexes = {@Index(name = "idx_usuario_username", columnList = "username")}
)
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @NonNull
    @JsonIgnore
    @Column(name = "password_hash", nullable = false,length = 128)
    private String passwordHash;

    @NonNull
    @Column(nullable = false, length = 20)
    private String nombre;

    @NonNull
    @Column(nullable = false, length = 20)
    private String email;

    @NonNull
    @Column(nullable = false,length = 9)
    private String telefono;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;
}
