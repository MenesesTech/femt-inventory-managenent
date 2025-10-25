package com.femt.inventory_management.models.produccion;

import com.femt.inventory_management.models.produccion.enums.TipoProductoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "produccion_tipo")
public class TipoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", nullable = false, length = 20)
    private TipoProductoEnum tipoProducto;

    // Para ver las tallas de un tipo de calzado
    @NonNull
    @OneToMany(
            mappedBy = "tipoProducto",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<TallaProducto> tallaProductos = new ArrayList<>();
}
