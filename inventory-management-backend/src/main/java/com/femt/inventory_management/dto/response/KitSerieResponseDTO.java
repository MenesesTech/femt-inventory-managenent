package com.femt.inventory_management.dto.response;

public record KitSerieResponseDTO(
        Integer id,
        Integer idSerieCode,
        Integer idModelo,
        Integer idTalla,
        Integer idColor,
        Integer idCategoria,
        Integer idTipoComponente,
        String serieCode,
        String modelo,
        String talla,
        String color,
        String categoria,
        String tipoComponente
) {}
