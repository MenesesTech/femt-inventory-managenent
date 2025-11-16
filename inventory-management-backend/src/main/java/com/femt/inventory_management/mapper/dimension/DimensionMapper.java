package com.femt.inventory_management.mapper.dimension;

import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.models.dimension.DimBase;
import com.femt.inventory_management.models.dimension.DimColor;
import org.springframework.stereotype.Component;

@Component
public class DimensionMapper {
    public DimensionResponseDTO toDTO(DimBase dim) {
        String codeRgb = null;
        if (dim instanceof DimColor color) {
            codeRgb = color.getCodeRgb();
        }

        return new DimensionResponseDTO(
                dim.getId(),
                dim.getNombre(),
                codeRgb
        );
    }
}
