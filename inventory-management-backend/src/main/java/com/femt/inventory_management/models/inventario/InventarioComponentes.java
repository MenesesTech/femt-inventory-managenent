package com.femt.inventory_management.models.inventario;

import com.femt.inventory_management.models.dimension.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "inventario_componentes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_sku_componente",
                        columnNames = {"id_categoria", "id_modelo", "id_tipo_componente", "id_talla", "id_color"}
                )
        }
)
public class InventarioComponentes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stock_pares", nullable = false)
    private Integer stockPares = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false,
            foreignKey = @ForeignKey(name = "dim_categoria_inv_componentes_fk"))
    private DimCategoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modelo", nullable = false,
            foreignKey = @ForeignKey(name = "dim_model_inv_componentes_fk"))
    private DimModelo modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_componente", nullable = false,
            foreignKey = @ForeignKey(name = "dim_tipo_comp_inv_componente_fk"))
    private DimTipoComponente tipoComponente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_talla", nullable = false,
            foreignKey = @ForeignKey(name = "dim_talla_inv_componente_fk"))
    private DimTalla talla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_color", nullable = false,
            foreignKey = @ForeignKey(name = "dim_color_inv_componente_fk"))
    private DimColor color;
}
