package com.femt.inventory_management.service.ventas;

import com.femt.inventory_management.dto.request.VentasComprobantePagoRequestDTO;
import com.femt.inventory_management.dto.response.VentasComprobantePagoResponseDTO;

import java.util.List;

public interface VentasComprobantePagoService {
    VentasComprobantePagoResponseDTO crear(VentasComprobantePagoRequestDTO dto);
    VentasComprobantePagoResponseDTO actualizar(Integer id, VentasComprobantePagoRequestDTO dto);
    void eliminar(Integer id);
    VentasComprobantePagoResponseDTO obtenerPorId(Integer id);
    List<VentasComprobantePagoResponseDTO> listar();

}
