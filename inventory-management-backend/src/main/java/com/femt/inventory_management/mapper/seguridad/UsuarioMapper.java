package com.femt.inventory_management.mapper.seguridad;

import com.femt.inventory_management.dto.request.UsuarioRequestDTO;
import com.femt.inventory_management.dto.response.UsuarioResponseDTO;
import com.femt.inventory_management.models.seguridad.Rol;
import com.femt.inventory_management.models.seguridad.Usuario;
import com.femt.inventory_management.models.seguridad.enums.RolUsuarioEnum;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public UsuarioResponseDTO toDTO(Usuario usuario){
        if (usuario == null) {
            return null;
        }
        return new UsuarioResponseDTO(
                usuario.getAuth0Id(),
                usuario.getRol() != null ? usuario.getRol().getRolUsuario().toString(): "SIN_ROL",
                convertUserState(usuario.isActivo()),
                usuario.getFechaCreacion() != null ? usuario.getFechaCreacion().toString(): "SIN_FECHA",
                usuario.getFechaActualizacion() !=  null ? usuario.getFechaActualizacion().toString(): "SIN_FECHA"
        );
    }

    public Usuario toEntity(UsuarioRequestDTO usuarioRequestDTO){
        if (usuarioRequestDTO == null){
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setAuth0Id(usuarioRequestDTO.auth0Id());
        usuario.setActivo(usuarioRequestDTO.estado());
        usuario.setRol(new Rol(usuarioRequestDTO.rol()));
       return usuario;
    }

    public String convertUserState(Boolean state){
        if (state == null){
            return "NO DEFINIDO";
        }
        return state ? "ACTIVADO":"DESACTIVADO";
    }
}
