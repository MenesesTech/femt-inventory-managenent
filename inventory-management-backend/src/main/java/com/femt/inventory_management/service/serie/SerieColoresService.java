package com.femt.inventory_management.service.serie;

import com.femt.inventory_management.dto.request.KitSerieBatchRequestDTO;
import com.femt.inventory_management.dto.request.KitSerieColorBatchRequest;
import com.femt.inventory_management.dto.response.KitSerieColorResponseDTO;
import com.femt.inventory_management.dto.response.KitSerieResponseDTO;

import java.util.List;

public interface SerieColoresService {
    List<KitSerieColorResponseDTO> guardarSerieColores(KitSerieColorBatchRequest KitSerieColorBatchRequest);
    KitSerieColorResponseDTO obtenerPorId(Integer id);
    List<KitSerieColorResponseDTO> obtenerPorModeloCategoriaTipo(
            Integer idModelo,
            Integer idCategoria,
            Integer idTipoComponente
    );
    KitSerieColorResponseDTO actualizarColor(Integer id, Integer nuevoColorId);
    void eliminarPorModeloCategoriaTipo(
            Integer idModelo,
            Integer idCategoria,
            Integer idTipoComponente
    );
}
