package com.femt.inventory_management.models.ventas;

import com.femt.inventory_management.models.seguridad.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "ventas_venta")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(nullable = false)
    private BigDecimal igv;

    @NonNull
    @Column(nullable = true)
    private BigDecimal descuento;

    @NonNull
    @Column(name="monto_total", nullable = false)
    private BigDecimal montoTotal;

    @NonNull
    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
