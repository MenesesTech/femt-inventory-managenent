package com.femt.inventory_management.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VentasPedidoResponseDTO(
        Integer id,
        Integer idCliente,
        LocalDate fechaCreada,
        LocalDate fechaEntrega,
        Integer idUsuario,
        Integer idKitSerieManager,
        BigDecimal montoTotal
) {
}
