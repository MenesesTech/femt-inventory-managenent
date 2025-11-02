package com.femt.inventory_management.dto.request;

// DTO para crear orden de produccion de componentes
public record OrdenComponenteRequestDTO(
        Integer idCategoria,
        Integer idModelo,
        Integer idSerie,
        Integer idTipoComponente,
        Integer cantidadPares
) {}
