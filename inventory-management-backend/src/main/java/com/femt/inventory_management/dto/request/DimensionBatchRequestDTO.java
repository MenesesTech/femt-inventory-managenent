package com.femt.inventory_management.dto.request;

import java.util.List;

public record DimensionBatchRequestDTO(
        List<DimensionRequestDTO> dimensiones
) {}
