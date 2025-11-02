package com.femt.inventory_management.models.produccion;

import com.femt.inventory_management.models.dimension.DimCategoria;
import com.femt.inventory_management.models.dimension.DimModelo;
import com.femt.inventory_management.models.dimension.DimSerie;
import com.femt.inventory_management.models.dimension.DimTipoComponente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * <p>Esta tabla representa la primera fase de producción es como la <b>solicitud formal</b> para producir un lote de
 * componentes (Plantas o Tiras). Es el inicio del flujo de producción.</p>
 * <p>Una orden inicia en estado "PENDIENTE" y es procesada por @ProduccionService
 * para convertirse en "TERMINADO", lo que a su vez incrementa el stock en {@link com.femt.inventory_management.models.inventario.InventarioComponentes}.</p>
 *
 * <>
 * @author MenesesTech
 * @version 1.0
 * @since 2025-11-01
 */
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
