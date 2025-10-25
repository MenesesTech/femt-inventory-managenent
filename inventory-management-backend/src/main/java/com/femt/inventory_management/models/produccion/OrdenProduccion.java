package com.femt.inventory_management.models.produccion;

import com.femt.inventory_management.models.inventario.InventarioProducto;
import com.femt.inventory_management.models.seguridad.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "produccion_solicitud_de_produccion")
public class OrdenProduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(nullable = false)
    private Integer cantidad;

    @NonNull
    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    // Muchas órdenes pertenecen a un usuario
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // Muchas órdenes usan una misma planta/tira
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_planta_tira", nullable = false)
    private ParteProducto parteProducto;

    // Muchas órdenes usan una misma pegatina decorativa
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pegatina", nullable = false)
    private PegatinaDecorativa pegatinaDecorativa;

    // Una orden de producción puede generar varios productos en inventario
    @NonNull
    @OneToMany(
            mappedBy = "ordenProduccion",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<InventarioProducto> inventarios = new ArrayList<>();

}
