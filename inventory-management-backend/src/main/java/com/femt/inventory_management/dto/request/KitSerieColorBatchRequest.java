package com.femt.inventory_management.dto.request;

import java.util.List;

public record KitSerieColorBatchRequest(
        List<KitSerieColorRequestDTO> colores
)
{}
