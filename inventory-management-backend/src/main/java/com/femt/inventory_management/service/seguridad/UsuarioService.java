package com.femt.inventory_management.service.seguridad;

import com.femt.inventory_management.dto.request.UsuarioRequestDTO;
import com.femt.inventory_management.dto.response.UsuarioResponseDTO;
import com.femt.inventory_management.models.seguridad.Usuario;

import java.util.List;

public interface  UsuarioService {
    List<UsuarioResponseDTO> listarUsuarios() throws Exception;
    Usuario modificarUsuario(UsuarioRequestDTO request) throws Exception;
    void eliminarUsuario(String auth0Id) throws Exception;
}
