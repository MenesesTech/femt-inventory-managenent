package com.femt.inventory_management.service.seguridad.imp;

import com.femt.inventory_management.models.seguridad.Rol;
import com.femt.inventory_management.models.seguridad.Usuario;
import com.femt.inventory_management.models.seguridad.enums.RolUsuarioEnum;
import com.femt.inventory_management.repository.SegRolRepository;
import com.femt.inventory_management.repository.SegUsuarioRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * ==========================================================
 * Servicio Personalizado de Usuarios OIDC (Integración Auth0)
 * ==========================================================
 * <>
 *     Implementación de {@link OidcUserService} para integrar la autenticación
 *     mediante OpenID Connect (OIDC) con Auth0 como proveedor de identidad.
 *     <>
 *         Durante el flujo de autenticación, este servicio valida y sincroniza
 *         los usuarios externos (Auth0) con la base de datos interna del sistema.
 *         Si el usuario no existe, se crea automáticamente con estado inactivo
 *         y rol por defecto EMPLEADO.
 *     <>
 *         Responsabilidades:
 *         - Validar existencia del usuario autenticado en la base interna
 *         - Registrar automáticamente nuevos usuarios (rol EMPLEADO)
 *         - Restringir acceso a cuentas inactivas
 *         - Asignar roles (authorities) a la sesión de Spring Security
 *     <>
 * Forma parte del flujo de seguridad definido en
 * {@link com.femt.inventory_management.security.SecurityConfig}.
 * <>
 *
 * @author  MenesesTech
 * @version 1.2
 * @since   2025-10
 */
@Service
public class CustomOidcUserService extends OidcUserService {

    private final SegUsuarioRepository segUsuarioRepository;
    private final SegRolRepository rolRepository;

    /**
     * Constructor principal del servicio.
     * <>
     * @param segUsuarioRepository Repositorio de usuarios internos
     * @param rolRepository Repositorio de roles del sistema
     */
    public CustomOidcUserService(SegUsuarioRepository segUsuarioRepository, SegRolRepository rolRepository) {
        this.segUsuarioRepository = segUsuarioRepository;
        this.rolRepository = rolRepository;
    }

    /**
     * =======================================================
     * Carga y Sincronización de Usuario Autenticado (OIDC)
     * =======================================================
     * <>
     *     Método principal del servicio, invocado automáticamente durante
     *     el flujo de autenticación OIDC por Spring Security.
     *     <>
     *         Flujo de ejecución:
     *         1. Carga los datos del usuario autenticado desde Auth0
     *         2. Busca o crea el usuario interno (según su auth0Id)
     *         3. Valida el estado de la cuenta interna (activo/inactivo)
     *         4. Construye y retorna la instancia de {@link OidcUser} con los roles asignados
     *     <>
     *         Excepciones:
     *         - DisabledException → cuenta pendiente de aprobación
     *         - OAuth2AuthenticationException → error general de autenticación OIDC
     *         - RuntimeException → rol por defecto no configurado en BD
     *     <>
     * @param userRequest Solicitud OIDC con tokens e información del usuario autenticado
     * @return Instancia de {@link OidcUser} con roles internos y datos OIDC
     * @throws OAuth2AuthenticationException Si ocurre un error durante la autenticación
     */
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. Cargar usuario desde el proveedor externo (Auth0)
        OidcUser oidcUser = super.loadUser(userRequest);
        String auth0Id = oidcUser.getSubject();

        // 2. Buscar o registrar usuario interno
        Usuario usuarioInterno = findOrCreateUsuario(auth0Id);

        // 3. Validar estado de la cuenta interna
        if (!usuarioInterno.isActivo()) {
            throw new DisabledException("Su cuenta ha sido registrada y está pendiente de aprobación por un administrador.");
        }

        // 4. Asignar autoridades (roles internos)
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuarioInterno.getRol().getRolUsuario().name()));

        // 5. Retornar el usuario OIDC enriquecido con roles internos
        return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
    }

    /**
     * ======================================================
     * Creación Automática del Usuario Interno (Transaccional)
     * ======================================================
     * <>
     *     Busca el usuario interno por su identificador Auth0.
     *     Si no existe, crea un nuevo registro en estado inactivo con el rol
     *     por defecto EMPLEADO.
     *     Se ejecuta en una transacción independiente (REQUIRES_NEW) para
     *     garantizar persistencia incluso si el flujo superior falla.
     *     <>
     * @param auth0Id Identificador único del usuario proveniente de Auth0
     * @return Usuario interno existente o recién creado
     * @throws RuntimeException Si el rol por defecto EMPLEADO no está configurado
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Usuario findOrCreateUsuario(String auth0Id) {
        return segUsuarioRepository.findByAuth0Id(auth0Id)
                .orElseGet(() -> {
                    Rol rolDefault = rolRepository.findByRolUsuario(RolUsuarioEnum.EMPLEADO)
                            .orElseThrow(() -> new RuntimeException("Error: Rol por defecto EMPLEADO no encontrado."));
                    Usuario nuevoUsuario = new Usuario(auth0Id, false, rolDefault);
                    return segUsuarioRepository.save(nuevoUsuario);
                });
    }
}
