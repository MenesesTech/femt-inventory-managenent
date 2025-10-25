package com.femt.inventory_management.models.ventas;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "ventas_cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "nombre_razon_social", nullable = false, length = 30)
    private String nombreRazonSocial;

    @NonNull
    @Column(nullable = false, length = 30)
    private String apellido;

    @NonNull
    @Column(nullable = false, length = 50)
    private String direccion;

    @NonNull
    @Column(nullable = false, length = 9)
    private String telefono;

    @NonNull
    @Column(nullable = false, length = 10)
    private String ruc;

    @NonNull
    @Column(nullable = false, length = 8)
    private String dni;

    // Acceder desde clientes a sus pedidos
    @NonNull
    @OneToMany(
            mappedBy = "cliente",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Pedido> pedidos = new ArrayList<>();
}
