package com.femt.inventory_management.dto.request;

import java.time.LocalDate;

public record VentasComprobantePagoRequestDTO(
        String tipoComprobante,
        String serie,
        LocalDate fechaEmision,
        String estadoSunat,
        String archivoXml,
        String archivoCdr,
        Integer idVenta
) {
}
