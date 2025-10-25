package com.femt.inventory_management.models.produccion;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "produccion_planta_tira")
public class ParteProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(nullable = false, length = 50)
    private String nombre;

    @NonNull
    @Column(nullable = false)
    private Integer cantidad;

    // Muchas plantas y tiras en un solo color
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_color", nullable = false)
    private ColorProducto colorProducto;

    // Muchas plantas y tiras en una sola categoria
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaProducto categoriaProducto;

    // Muchas plantas y tiras en una sola talla
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_talla", nullable = false)
    private TallaProducto tallaProducto;

    // Muchas plantas y tiras en un solo tipo
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo", nullable = false)
    private TipoProducto tipoProducto;

    // Muchas plantas y tiras en una sola compra
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compra", nullable = false)
    private Compra compra;
}
