package com.femt.inventory_management.model.produccion;

import com.femt.inventory_management.model.seguridad.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
}
