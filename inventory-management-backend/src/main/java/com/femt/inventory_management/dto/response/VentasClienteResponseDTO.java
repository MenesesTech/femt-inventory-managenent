package com.femt.inventory_management.dto.response;

public record VentasClienteResponseDTO(
        Integer id,
        String nombreRazonSocial,
        String apellido,
        String direccion,
        String telefono,
        String ruc,
        String dni
) {
}
