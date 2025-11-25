package com.femt.inventory_management.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VentasPedidoRequestDTO(
        Integer idCliente,
        LocalDate fechaCreada,
        LocalDate fechaEntrega,
        Integer idUsuario,
        Integer idKitSerieManager,
        BigDecimal montoTotal
) {
}
