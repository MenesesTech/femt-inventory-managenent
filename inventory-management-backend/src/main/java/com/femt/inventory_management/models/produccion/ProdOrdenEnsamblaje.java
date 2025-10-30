package com.femt.inventory_management.models.produccion;

import com.femt.inventory_management.models.inventario.InventarioProductoTerminado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
