package com.femt.inventory_management.models.inventario;

import com.femt.inventory_management.models.dimension.DimCategoria;
import com.femt.inventory_management.models.dimension.DimColor;
import com.femt.inventory_management.models.dimension.DimModelo;
import com.femt.inventory_management.models.dimension.DimTalla;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>Esta tabla representa el stock del <b>producto final ensamblado</b>
 * (la sandalia completa), que está listo para la venta.</p>
 *
 * <p>El stock de esta tabla <b>aumenta</b> cuando se crea una
 * {@link com.femt.inventory_management.models.produccion.ProdOrdenEnsamblaje}
 * (lo que consume stock de {@link InventarioComponentes}).</p>
 *
 * <p>El stock <b>disminuye</b> cuando se genera una venta
 * ({@link com.femt.inventory_management.models.ventas.VentasPedidoDetalle}).</p>
 *
 * <p>La restricción {@code @UniqueConstraint} define el <b>SKU del Producto Final</b>.
 * Nótese que no incluye "tipo_componente" (porque es una sandalia completa)
 * pero sí incluye <b>dos campos de color</b> para la planta y la tira.</p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventario_producto_terminado",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_sku_producto_final",
                columnNames = {"id_categoria", "id_modelo", "id_talla", "id_color_planta", "id_color_tira"}
        ))
public class InventarioProductoTerminado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stock_pares", nullable = false)
    private Integer stockPares = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false,
            foreignKey = @ForeignKey(name = "dim_categoria_inv_prod_terminado_fk"))
    private DimCategoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modelo", nullable = false,
            foreignKey = @ForeignKey(name = "dim_modelo_inv_prod_terminado_fk"))
    private DimModelo modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_talla", nullable = false,
            foreignKey = @ForeignKey(name = "dim_talla_inv_prod_terminado_fk"))
    private DimTalla talla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_color_planta", nullable = false,
            foreignKey = @ForeignKey(name = "dim_color_planta_inv_prod_terminado_fk"))
    private DimColor colorPlanta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_color_tira", nullable = false,
            foreignKey = @ForeignKey(name = "dim_color_tira_inv_prod_terminado_fk"))
    private DimColor colorTira;
}
