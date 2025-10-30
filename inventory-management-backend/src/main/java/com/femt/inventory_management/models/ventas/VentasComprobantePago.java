package com.femt.inventory_management.models.ventas;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ventas_comprobante_pago")
public class VentasComprobantePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo_comprobante", nullable = false, length = 20)
    private String tipoComprobante;

    @Column(nullable = false, length = 20)
    private String serie;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "estado_sunat", nullable = false, length = 20)
    private String estadoSunat;

    @Column(name = "archivo_xml", nullable = false, length = 100)
    private String archivoXml;

    @Column(name = "archivo_cdr", length = 100)
    private String archivoCdr;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venta", nullable = false,
            foreignKey = @ForeignKey(name = "ventas_venta_comprobante_fk"))
    private VentasVenta venta;
}
