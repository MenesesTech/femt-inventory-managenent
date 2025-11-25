package com.femt.inventory_management.service.ventas;

import com.femt.inventory_management.dto.request.VentasClienteRequestDTO;
import com.femt.inventory_management.dto.response.VentasClienteResponseDTO;

import java.util.List;

public interface VentasClienteService {
    VentasClienteResponseDTO crear(VentasClienteRequestDTO dto);
    VentasClienteResponseDTO actualizar(Integer id, VentasClienteRequestDTO dto);
    void eliminar(Integer id);
    VentasClienteResponseDTO obtenerPorId(Integer id);
    List<VentasClienteResponseDTO> listar();

}
