package com.femt.inventory_management.service.dimension.imp;

import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.service.dimension.DimensionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService implements DimensionService {
    /**
     * @param dto
     * @return
     */
    @Override
    public DimensionResponseDTO crear(DimensionRequestDTO dto) {
        return null;
    }

    /**
     * @param id
     * @param dto
     * @return
     */
    @Override
    public DimensionResponseDTO actualizar(Integer id, DimensionRequestDTO dto) {
        return null;
    }

    /**
     * @param id
     */
    @Override
    public void eliminar(Integer id) {

    }

    /**
     * @param id
     * @return
     */
    @Override
    public DimensionResponseDTO buscarPorId(Integer id) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public List<DimensionResponseDTO> listarTodo() {
        return List.of();
    }
}
