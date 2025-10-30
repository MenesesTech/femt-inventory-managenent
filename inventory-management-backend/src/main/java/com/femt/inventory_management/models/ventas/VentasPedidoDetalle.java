package com.femt.inventory_management.models.ventas;

import com.femt.inventory_management.models.inventario.InventarioProductoTerminado;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ventas_pedido_detalle")
public class VentasPedidoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false,
            foreignKey = @ForeignKey(name = "ventas_pedido_detalle_fk"))
    private VentasPedido pedido;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto_terminado_sku", nullable = false,
            foreignKey = @ForeignKey(name = "inv_producto_terminado_detalle_fk"))
    private InventarioProductoTerminado productoTerminado;

    @Column(name = "cantidad_pares", nullable = false)
    private Integer cantidadPares;

    @Column(name = "precio_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioUnitario;
}
