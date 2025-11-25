package com.femt.inventory_management.dto.response;

import java.math.BigDecimal;

public record VentasPedidoDetalleResponseDTO(
        Integer id,
        Integer idPedido,
        Integer cantidadDocenas,
        BigDecimal precioUnitario
) {
}
