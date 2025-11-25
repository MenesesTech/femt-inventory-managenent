package com.femt.inventory_management.dto.response;

import java.time.LocalDate;

public record VentasComprobantePagoResponseDTO(
        Integer id,
        String tipoComprobante,
        String serie,
        LocalDate fechaEmision,
        String estadoSunat,
        String archivoXml,
        String archivoCdr,
        Integer idVenta
) {
}
