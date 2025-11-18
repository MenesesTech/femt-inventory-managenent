package com.femt.inventory_management.service.dimension;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;

import java.util.List;

public interface DimensionService {
    List<DimensionResponseDTO> crear(DimensionBatchRequestDTO dto);

    DimensionResponseDTO actualizar(Integer id, DimensionRequestDTO dto);

    void eliminar(Integer id);

    DimensionResponseDTO buscarPorId(Integer id);

    List<DimensionResponseDTO> listarTodo();
}
