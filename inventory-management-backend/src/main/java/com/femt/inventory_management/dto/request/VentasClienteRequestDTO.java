package com.femt.inventory_management.dto.request;

public record VentasClienteRequestDTO(
        String nombreRazonSocial,
        String apellido,
        String direccion,
        String telefono,
        String ruc,
        String dni
) {
}
