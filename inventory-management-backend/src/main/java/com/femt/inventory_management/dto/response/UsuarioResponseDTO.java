package com.femt.inventory_management.dto.response;

public record UsuarioResponseDTO(String auth0Id, String rol, String activo, String fechaCreacion, String fechaActualizacion) {
}
