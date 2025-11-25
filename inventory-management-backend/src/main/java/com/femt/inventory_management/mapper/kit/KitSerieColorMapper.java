package com.femt.inventory_management.mapper.kit;

import com.femt.inventory_management.dto.response.KitSerieColorResponseDTO;
import com.femt.inventory_management.models.kit.KitSerieColor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KitSerieColorMapper {
    public KitSerieColorResponseDTO toDTO(KitSerieColor kitSerieColor){
        if (kitSerieColor == null){
            return null;
        }

        return new KitSerieColorResponseDTO(
                kitSerieColor.getId(),
                kitSerieColor.getFila().getId(),
                kitSerieColor.getColumna().getId(),
                kitSerieColor.getModelo().getId(),
                kitSerieColor.getColor().getId(),
                kitSerieColor.getCategoria().getId(),
                kitSerieColor.getTipoComponente().getId(),
                kitSerieColor.getColumna().getNombre(),
                kitSerieColor.getFila().getNombre(),
                kitSerieColor.getModelo().getNombre(),
                kitSerieColor.getColor().getNombre(),
                kitSerieColor.getCategoria().getNombre(),
                kitSerieColor.getTipoComponente().getNombre()
        );
    }

    public List<KitSerieColorResponseDTO> toDTOList(List<KitSerieColor> kitSerieColores){
        if (kitSerieColores == null || kitSerieColores.isEmpty()){
            return List.of();
        }
        return kitSerieColores.stream()
                .map(this::toDTO)
                .toList();
    }
}
