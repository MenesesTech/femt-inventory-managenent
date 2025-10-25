package com.femt.inventory_management.models.produccion;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "produccion_talla")
public class TallaProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "min_talla", nullable = false)
    private Integer minTalla;

    @NonNull
    @Column(name = "max_talla", nullable = false)
    private Integer maxTalla;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo", nullable = false)
    private TipoProducto tipoProducto;
}
