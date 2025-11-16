package com.femt.inventory_management.service.serie;

import com.femt.inventory_management.dto.request.KitSerieBatchRequestDTO;
import com.femt.inventory_management.dto.response.KitSerieResponseDTO;
import com.femt.inventory_management.models.kit.KitSerieCode;

import java.util.List;

public interface SerieColoresService {
    List<KitSerieResponseDTO> guardarSerie(KitSerieBatchRequestDTO requestDTO);

    List<KitSerieResponseDTO> listarTodo();

    List<KitSerieResponseDTO> listarPorModeloCategoria(Integer idModelo, Integer idCategoria);

    List<KitSerieResponseDTO> obtenerTablaSeries(Integer idModelo, Integer idCategoria, Integer idSerieCode);

    KitSerieResponseDTO actualizarSerie(Integer idSerie, Integer idColorNuevo);

    void eliminarSerie(Integer idSerie);
}
