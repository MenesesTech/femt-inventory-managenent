package com.femt.inventory_management.model.produccion;

import com.femt.inventory_management.model.produccion.enums.PegatinaProductoEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "produccion_pegatina")
public class PegatinaDecorativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", nullable = false, length = 20)
    private PegatinaProductoEnum pegatinaProducto;

    @NonNull
    @Column(nullable = false)
    private Integer cantidad;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compra", nullable = false)
    private Compra compra;
}
