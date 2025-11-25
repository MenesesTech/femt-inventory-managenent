package com.femt.inventory_management.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VentasVentaResponseDTO(
        Integer id,
        BigDecimal igv,
        BigDecimal descuento,
        BigDecimal montoTotal,
        LocalDate fechaPago,
        Integer idCliente,
        Integer idPedido,
        Integer idUsuario
) {
}
