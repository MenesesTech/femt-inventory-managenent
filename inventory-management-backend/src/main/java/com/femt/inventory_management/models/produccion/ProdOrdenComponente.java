package com.femt.inventory_management.models.produccion;

import com.femt.inventory_management.models.dimension.DimCategoria;
import com.femt.inventory_management.models.dimension.DimModelo;
import com.femt.inventory_management.models.dimension.DimSerie;
import com.femt.inventory_management.models.dimension.DimTipoComponente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prod_orden_componente")
public class ProdOrdenComponente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cantidad_pares", nullable = false)
    private Integer cantidadPares;

    @Column(nullable = false)
    private LocalDate fechaCreacion;

    @Column(nullable = false, length = 20)
    private String estado = "PENDIENTE";

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false,
            foreignKey = @ForeignKey(name = "dim_categoria_prod_orden_componente"))
    private DimCategoria dimCategoria;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_serie", nullable = false,
            foreignKey = @ForeignKey(name = "dim_serie_prod_orden_componente"))
    private DimSerie dimSerie;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modelo", nullable = false,
            foreignKey = @ForeignKey(name = "dim_model_prod_orden_componente"))
    private DimModelo modelo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_componente", nullable = false,
            foreignKey = @ForeignKey(name = "dim_tipo_comp_prod_orden_componente"))
    private DimTipoComponente tipoComponente;

}
