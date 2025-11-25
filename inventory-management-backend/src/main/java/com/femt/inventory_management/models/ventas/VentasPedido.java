package com.femt.inventory_management.models.ventas;

import com.femt.inventory_management.models.kit.KitSerieManager;
import com.femt.inventory_management.models.seguridad.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ventas_pedido")
public class VentasPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false,
            foreignKey = @ForeignKey(name = "ventas_cliente_pedido_fk"))
    private VentasCliente cliente;

    @Column(name = "fecha_creada", nullable = false)
    private LocalDate fechaCreada;

    @Column(name = "fecha_entrega", nullable = false)
    private LocalDate fechaEntrega;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false,
            foreignKey = @ForeignKey(name = "seguridad_usuario_pedido_fk"))
    private Usuario usuario;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_kit_serie_manager", nullable = false,
            foreignKey = @ForeignKey(name = "kit_serie_manager_pedido_fk"))
    private KitSerieManager kitSerieManager;

    @Column(name = "monto_total", precision = 10, scale = 2, nullable = false)
    private BigDecimal montoTotal = BigDecimal.ZERO;
}