package com.femt.inventory_management.service.ventas;

import com.femt.inventory_management.dto.request.VentasVentaRequestDTO;
import com.femt.inventory_management.dto.response.VentasVentaResponseDTO;

import java.util.List;

public interface VentasVentaService {
    VentasVentaResponseDTO crear(VentasVentaRequestDTO dto);
    VentasVentaResponseDTO actualizar(Integer id, VentasVentaRequestDTO dto);
    void eliminar(Integer id);
    VentasVentaResponseDTO obtenerPorId(Integer id);
    List<VentasVentaResponseDTO> listar();
}
