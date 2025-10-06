package com.femt.inventory_management.model.ventas;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "ventas_comporbante_pago")
public class ComprobantePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // FACTURA o BOLETA
    @NonNull
    @Column(name = "tipo_comprobante", nullable = false, length = 20)
    private String tipoComprobante;

    @NonNull
    @Column(nullable = false, length = 20)
    private String serie;

    @NonNull
    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    // ACEPTADO, RECHAZADO, EN_PROCESO
    @NonNull
    @Column(name = "estado_sunat", nullable = false, length = 20)
    private String estadoSunat;

    @NonNull
    @Column(name = "archivo_xml", nullable = false, length = 100)
    private String archivoXml;

    @Column(name = "archivo_cdr", length = 100)
    private String archivoCdr;

    // Relación con la tabla ventas_venta
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;
}
