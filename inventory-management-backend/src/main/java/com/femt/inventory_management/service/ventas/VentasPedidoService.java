package com.femt.inventory_management.service.ventas;

import com.femt.inventory_management.dto.request.VentasPedidoRequestDTO;
import com.femt.inventory_management.dto.response.VentasPedidoResponseDTO;

import java.util.List;

public interface VentasPedidoService {
    VentasPedidoResponseDTO crear(VentasPedidoRequestDTO dto);
    VentasPedidoResponseDTO actualizar(Integer id, VentasPedidoRequestDTO dto);
    void eliminar(Integer id);
    VentasPedidoResponseDTO obtenerPorId(Integer id);
    List<VentasPedidoResponseDTO> listar();
}
