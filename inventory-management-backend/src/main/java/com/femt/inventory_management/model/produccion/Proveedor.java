package com.femt.inventory_management.model.produccion;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "produccion_proveedor")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "nombre_razon_social", nullable = false, length = 30)
    private String nombreRazonSocial;

    @NonNull
    @Column(nullable = true, length = 30)
    private String apellido;

    @NonNull
    @Column(nullable = true, length = 50)
    private String direccion;

    @NonNull
    @Column(nullable = true,length = 9)
    private String telefono;

    @NonNull
    @Column(nullable = true, length = 10)
    private String ruc;


    // Para ver las compras de un proveedor
    @OneToMany(
            mappedBy = "proveedor",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY

    )
    private List<Compra> compras = new ArrayList<>();
}
