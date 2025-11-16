package com.femt.inventory_management.models.inventario;

import com.femt.inventory_management.models.dimension.DimModelo;
import com.femt.inventory_management.models.kit.KitSerie;
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
        name = "inv_sandalias",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_sku_sandalias",
                        columnNames = {"id_modelo", "id_serie_tira", "id_serie_planta","id_categoria"}
                )
        }
)
public class InventarioSandalias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modelo", nullable = false,
            foreignKey = @ForeignKey(name = "dim_model_inv_componentes_fk"))
    private DimModelo modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_serie_tira", nullable = false,
            foreignKey = @ForeignKey(name = "serie_tira_inv_sandalias_fk"))
    private KitSerie serieTira;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_serie_planta", nullable = false,
            foreignKey = @ForeignKey(name = "serie_planta_inv_sandalias_fk"))
    private KitSerie seriePlanta;
}
