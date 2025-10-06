package com.femt.inventory_management.model.produccion;

import com.femt.inventory_management.model.produccion.enums.EstadoCompraEnum;
import com.femt.inventory_management.model.produccion.enums.MetodoPagoEnum;
import com.femt.inventory_management.model.produccion.enums.TipoComprobanteEnum;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "produccion_compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(nullable = false)
    private LocalDate fechaCompra;

    @NonNull
    @Column(name = "nro_comprobante", nullable = true, length = 10)
    private String numeroComprobante;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 10)
    private TipoComprobanteEnum tipoComprobante;

    @NonNull
    @Column(name = "monto_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPagoEnum metodoPago;

    @NonNull
    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private EstadoCompraEnum estado;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    // Para ver los detalles de compra de cada pegatina
    @OneToMany(
            mappedBy = "compra",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<PegatinaDecorativa> pegatinas = new ArrayList<>();
}
