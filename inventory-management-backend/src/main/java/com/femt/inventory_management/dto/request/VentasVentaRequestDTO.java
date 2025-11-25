package com.femt.inventory_management.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VentasVentaRequestDTO(
        BigDecimal igv,
        BigDecimal descuento,
        BigDecimal montoTotal,
        LocalDate fechaPago,
        Integer idCliente,
        Integer idPedido,
        Integer idUsuario
) {
}
