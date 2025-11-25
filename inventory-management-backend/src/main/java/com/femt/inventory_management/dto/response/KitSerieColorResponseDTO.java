package com.femt.inventory_management.dto.response;

public record KitSerieColorResponseDTO(
        Integer id,
        Integer idFila,
        Integer idColumna,
        Integer idModelo,
        Integer idColor,
        Integer idCategoria,
        Integer idTipoComponente,
        String columna,
        String fila,
        String modelo,
        String color,
        String categoria,
        String tipoComponente
) {}
