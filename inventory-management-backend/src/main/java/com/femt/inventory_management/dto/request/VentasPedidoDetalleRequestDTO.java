package com.femt.inventory_management.dto.request;

import java.math.BigDecimal;

public record VentasPedidoDetalleRequestDTO(
        Integer idPedido,
        Integer cantidadDocenas,
        BigDecimal precioUnitario
) {
}
