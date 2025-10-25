package com.femt.inventory_management.dto.request;

import com.femt.inventory_management.models.seguridad.enums.RolUsuarioEnum;

public record UsuarioRequestDTO(String auth0Id, boolean estado, RolUsuarioEnum rol) {
}
