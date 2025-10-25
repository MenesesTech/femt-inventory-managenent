package com.femt.inventory_management.service.seguridad.imp;

import com.femt.inventory_management.dto.request.UsuarioRequestDTO;
import com.femt.inventory_management.dto.response.UsuarioResponseDTO;
import com.femt.inventory_management.mapper.seguridad.UsuarioMapper;
import com.femt.inventory_management.models.seguridad.Rol;
import com.femt.inventory_management.models.seguridad.Usuario;
import com.femt.inventory_management.repository.SegRolRepository;
import com.femt.inventory_management.repository.SegUsuarioRepository;
import com.femt.inventory_management.service.seguridad.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ==============================================================
 * Implementación del Servicio de Gestión de Usuarios Internos
 * ==============================================================
 * <>
 *     Servicio responsable de la administración y mantenimiento
 *     de los usuarios internos del sistema, incluyendo las
 *     operaciones de consulta, modificación y eliminación.
 *     <>
 *     Esta clase implementa la interfaz {@link UsuarioService},
 *     proporcionando la lógica de negocio asociada a la entidad
 *     {@link Usuario}, y utilizando el patrón DTO para facilitar
 *     la transferencia de datos entre capas.
 *     <>
 *     Funcionalidades principales:
 *     - Listar todos los usuarios del sistema
 *     - Modificar estado y rol de un usuario existente
 *     - Eliminar usuarios por su identificador Auth0
 *     <>
 *     La clase está anotada con {@link Service} para ser gestionada
 *     como un componente de servicio dentro del contenedor de Spring.
 * <>
 *
 * @author  MenesesTech
 * @version 1.1
 * @since   2025-10
 */
@Service
public class UsuarioServiceImp implements UsuarioService {

    private final SegUsuarioRepository segUsuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final SegRolRepository segRolRepository;

    /**
     * Constructor principal del servicio.
     * <>
     * Inicializa las dependencias necesarias para la gestión de usuarios.
     *
     * @param segUsuarioRepository Repositorio de persistencia de usuarios
     * @param usuarioMapper Mapper encargado de convertir entidades {@link Usuario}
     *                      a objetos de transferencia {@link UsuarioResponseDTO}
     */
    public UsuarioServiceImp(SegUsuarioRepository segUsuarioRepository,
                             UsuarioMapper usuarioMapper,
                             SegRolRepository segRolRepository) {
        this.segUsuarioRepository = segUsuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.segRolRepository = segRolRepository;
    }


    /**
     * =======================================================
     * Listado Completo de Usuarios del Sistema
     * =======================================================
     * <>
     *     Recupera todos los usuarios almacenados en la base de datos
     *     y los transforma a su representación de salida mediante el mapper.
     *     <>
     *     En caso de error en la consulta, se lanza una excepción
     *     {@link RuntimeException} con un mensaje descriptivo.
     * <>
     *
     * @return Lista de usuarios en formato {@link UsuarioResponseDTO}
     * @throws Exception Si ocurre un error durante la recuperación de datos
     */
    @Override
    public List<UsuarioResponseDTO> listarUsuarios() throws Exception {
        try {
            List<Usuario> usuarios = segUsuarioRepository.findAll();
            return usuarios.stream()
                    .map(usuarioMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de usuarios: " + e.getMessage());
        }
    }

    /**
     * =======================================================
     * Modificación de Usuario Existente
     * =======================================================
     * <>
     *     Actualiza los datos de un usuario identificado por su
     *     Auth0 ID, permitiendo modificar su estado y rol asignado.
     *     <>
     *     Flujo de ejecución:
     *     1. Validar la existencia del Auth0 ID
     *     2. Buscar el usuario correspondiente en la base de datos
     *     3. Actualizar su estado y rol
     *     4. Persistir los cambios
     *     <>
     *     Excepciones:
     *     - {@link IllegalArgumentException} → Si el Auth0 ID es nulo o inválido
     *     - {@link RuntimeException} → Si el usuario no se encuentra registrado
     * <>
     *
     * @param request Objeto {@link UsuarioRequestDTO} con los nuevos datos del usuario
     * @return Entidad {@link Usuario} actualizada y persistida
     * @throws Exception Si ocurre un error durante la actualización
     */
    @Override
    public Usuario modificarUsuario(UsuarioRequestDTO request) throws Exception {
        if (request == null || request.auth0Id() == null) {
            throw new IllegalArgumentException("El auth0Id es obligatorio para modificar el usuario.");
        }

        Usuario usuario = segUsuarioRepository.findByAuth0Id(request.auth0Id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con auth0Id: " + request.auth0Id()));

        // Buscar el rol existente
        Rol rolExistente = segRolRepository.findByRolUsuario(request.rol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + request.rol()));

        usuario.setActivo(request.estado());
        usuario.setRol(rolExistente);

        return segUsuarioRepository.save(usuario);
    }


    /**
     * =======================================================
     * Eliminación de Usuario Interno
     * =======================================================
     * <>
     *     Elimina de forma permanente un usuario identificado por su
     *     Auth0 ID del sistema interno.
     *     <>
     *     El proceso incluye una validación previa de existencia.
     *     Si el usuario no se encuentra, se lanza una excepción
     *     {@link RuntimeException}.
     * <>
     *
     * @param auth0Id Identificador único del usuario en Auth0
     * @throws Exception Si el usuario no existe o ocurre un error en la eliminación
     */
    @Override
    public void eliminarUsuario(String auth0Id) throws Exception {
        Optional<Usuario> usuarioOpt = segUsuarioRepository.findByAuth0Id(auth0Id);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado para eliminación: " + auth0Id);
        }
        segUsuarioRepository.delete(usuarioOpt.get());
    }
}
