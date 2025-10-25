package com.femt.inventory_management.models.inventario;

import com.femt.inventory_management.models.produccion.OrdenProduccion;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "inventario_producto")
public class InventarioProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(nullable = false)
    private Integer cantidad;

    @NonNull
    @Column(name = "precio_unitario", nullable = false)
    private BigDecimal precioUnitario;

    @NonNull
    @Column(nullable = false, length = 30)
    private String ubicacion;

    @NonNull
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    // Muchos registros de inventario pertenecen a una sola orden de producción
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud_produccion", nullable = false)
    private OrdenProduccion ordenProduccion;
}
