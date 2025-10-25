package com.femt.inventory_management.dto.request;

import java.time.LocalDateTime;

public record UsuarioAdminDTO(
    String auth0Id,
    boolean activo,
    String rol
) {}
