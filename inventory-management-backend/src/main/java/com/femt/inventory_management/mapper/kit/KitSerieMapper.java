package com.femt.inventory_management.mapper.kit;

import com.femt.inventory_management.dto.response.KitSerieResponseDTO;
import com.femt.inventory_management.models.kit.KitSerie;
import com.femt.inventory_management.models.kit.KitSerieCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KitSerieMapper {
    public KitSerieResponseDTO toDTO(KitSerie kitSerie){
        if (kitSerie == null){
            return null;
        }

        return new KitSerieResponseDTO(
                kitSerie.getId(),
                kitSerie.getSerieCode().getId(),
                kitSerie.getModelo().getId(),
                kitSerie.getTalla().getId(),
                kitSerie.getColor().getId(),
                kitSerie.getCategoria().getId(),
                kitSerie.getTipoComponente().getId(),

                kitSerie.getSerieCode().getSerieCode(),
                kitSerie.getModelo().getNombre(),
                kitSerie.getTalla().getNombre(),
                kitSerie.getColor().getNombre(),
                kitSerie.getCategoria().getNombre(),
                kitSerie.getTipoComponente().getNombre()
        );
    }

    public List<KitSerieResponseDTO> toDTOList(List<KitSerie> series){
        if (series == null || series.isEmpty()){
            return List.of();
        }

        return series.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
