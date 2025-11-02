package com.femt.inventory_management.models.produccion;

import com.femt.inventory_management.models.inventario.InventarioProductoTerminado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * <p>Esta tabla representa la <b>solicitud de ensamblaje</b> para crear
 * un producto terminado (sandalia) a partir de sus componentes.</p>
 * <p>Esta orden es procesada por el @EnsamblajeService
 * El proceso (en una sola transacción) <b>consume</b> stock de @InventarioComponentes
 * e <b>incrementa</b> el stock de {@link com.femt.inventory_management.models.inventario.InventarioProductoTerminado}.
 * </p>
 *
 * <p>Esta entidad sirve principalmente para la <b>trazabilidad</b> de las
 * operaciones de ensamblaje.</p>
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
@Table(name = "prod_orden_ensamblaje")
public class ProdOrdenEnsamblaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relación con inventario_producto_terminado
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto_terminado_sku", nullable = false,
            foreignKey = @ForeignKey(name = "prod_terminado_orden_ensamblaje_fk"))
    private InventarioProductoTerminado productoTerminado;

    @Column(name = "cantidad_a_ensamblar", nullable = false)
    private Integer cantidadAEnsamblar;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @Column(nullable = false, length = 20)
    private String estado = "PENDIENTE";
}
