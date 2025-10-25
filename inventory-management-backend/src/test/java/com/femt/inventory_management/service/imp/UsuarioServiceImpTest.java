package com.femt.inventory_management.service.imp;


import com.femt.inventory_management.dto.response.UsuarioResponseDTO;
import com.femt.inventory_management.mapper.seguridad.UsuarioMapper;
import com.femt.inventory_management.models.seguridad.Rol;
import com.femt.inventory_management.models.seguridad.Usuario;
import com.femt.inventory_management.models.seguridad.enums.RolUsuarioEnum;
import com.femt.inventory_management.repository.SegUsuarioRepository;
import com.femt.inventory_management.service.seguridad.imp.UsuarioServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Clase de prueba unitaria para UsuarioServiceImp.
 * Usamos Mockito para aislar el servicio de sus dependencias (repositorio y mapper).
 */
@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImpTest {
    // 1. Crear mock para las dependencias del servicio
    @Mock
    private SegUsuarioRepository segUsuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    // 2. Inyectar los mocks en la clase que queremos probar
    @InjectMocks
    private UsuarioServiceImp usuarioServiceImp;

    /**
     * Metodo de prueba que deberia retornar la lista de usuarios
     * @throws Exception
     */
    @Test
    void testListarUsuariosConUsuarios() throws Exception {
        // ----- ARRANGE (PREPARAR) -----

        // 1. Crear datos de prueba
        Usuario usuario1 = new Usuario("auth0|68fac5fa2fe318476c34cd9f", false, new Rol(RolUsuarioEnum.EMPLEADO));
        Usuario usuario2 = new Usuario("auth0|68fac5fa2fe318476c34cd4f", true, new Rol(RolUsuarioEnum.ADMIN));

        // Listamos usuarios
        List<Usuario> listaUsuarios = List.of(usuario1,usuario2);

        // Creamos los DTOS ( basado en la logica del Mapper )
        UsuarioResponseDTO dto1 = new UsuarioResponseDTO(
                "auth0|68fac5fa2fe318476c34cd9f",
                "EMPLEADO",
                "DESACTIVADO",
                "SIN_FECHA",
                "SIN FECHA"
        );
        UsuarioResponseDTO dto2 = new UsuarioResponseDTO(
                "auth0|68fac5fa2fe318476c34cd4f",
                "EMPLEADO",
                "ACTIVADO",
                "SIN_FECHA",
                "SIN_FECHA"
        );

        // Comportamiento de mocks
        // - Cuando se llame a segUsuarioRepository.findAll(), devuelve nuestra lista de usuarios
        when(segUsuarioRepository.findAll()).thenReturn(listaUsuarios);

        // Cuando se llame a usuarioMapper.toDTO con un usuario especifico, devuleve el DTO correspondiente
        when(usuarioMapper.toDTO(usuario1)).thenReturn(dto1);
        when(usuarioMapper.toDTO(usuario2)).thenReturn(dto2);

        // ----- ACTUAR(ACT) -----
        //Llamar al metodo que se está probando
        List<UsuarioResponseDTO> resultado = usuarioServiceImp.listarUsuarios();

        // ---- AFIRMAR(Afirmar) -----
        assertThat(resultado).isNotNull();
        assertThat(resultado).hasSize(2);

        // ---- Verificar que la lista de resultados contenga los DTOs esperados ----
        assertThat(resultado).containsExactlyInAnyOrder(dto1,dto2);

        // ---- Verificar que los mocks fueron llamados como se esperaba ---
        verify(segUsuarioRepository, times(1)).findAll();
        verify(usuarioMapper, times(1)).toDTO(usuario1);
        verify(usuarioMapper, times(1)).toDTO(usuario2);
    }

    @Test
    void testListarUsuariosSinUsuarios() throws Exception{
        // --- ARRANGE (PREPARAR) ---
        when(segUsuarioRepository.findAll()).thenReturn(Collections.emptyList());

        // -- ACT ---
        List<UsuarioResponseDTO> resultado = usuarioServiceImp.listarUsuarios();

        // --- ASSERT ---
        assertThat(resultado).isNotNull();
        assertThat(resultado).isEmpty();

        // Verificar que el repositorio fue llamado
        verify(segUsuarioRepository, times(1)).findAll();

        // Verificar que el mapper NUNCA fue llamado, ya que no habia nada que mapear
        verify(usuarioMapper,never()).toDTO(any(Usuario.class));
    }
}
