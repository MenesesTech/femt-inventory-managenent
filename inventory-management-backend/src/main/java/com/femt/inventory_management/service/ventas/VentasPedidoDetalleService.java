package com.femt.inventory_management.service.ventas;

import com.femt.inventory_management.dto.request.VentasPedidoDetalleRequestDTO;
import com.femt.inventory_management.dto.response.VentasPedidoDetalleResponseDTO;

import java.util.List;

public interface VentasPedidoDetalleService {
    VentasPedidoDetalleResponseDTO crear(VentasPedidoDetalleRequestDTO dto);
    VentasPedidoDetalleResponseDTO actualizar(Integer id, VentasPedidoDetalleRequestDTO dto);
    void eliminar(Integer id);
    VentasPedidoDetalleResponseDTO obtenerPorId(Integer id);
    List<VentasPedidoDetalleResponseDTO> listar();
}
