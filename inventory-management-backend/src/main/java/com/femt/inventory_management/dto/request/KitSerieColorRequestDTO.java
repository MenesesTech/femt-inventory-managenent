package com.femt.inventory_management.dto.request;

public record KitSerieColorRequestDTO(
        Integer idFila,
        Integer idColumna,
        Integer idModelo,
        Integer idColor,
        Integer idCategoria,
        Integer idTipoComponente
) {
}
