package com.femt.inventory_management.dto.request;

import java.util.List;

public record KitSerieBatchRequestDTO(
        Integer idModelo,
        Integer idCategoria,
        Integer idSerieCode,
        List<KitSerieItemRequestDTO> combinaciones
) {}
