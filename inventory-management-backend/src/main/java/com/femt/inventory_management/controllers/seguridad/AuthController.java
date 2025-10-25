package com.femt.inventory_management.controllers.seguridad;

import com.femt.inventory_management.models.seguridad.Usuario;
import com.femt.inventory_management.repository.SegUsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ==========================================================
 * Controlador de Autenticación y Perfil de Usuario
 * ==========================================================
 * <>
 *     Este controlador expone endpoints relacionados con la
 *     autenticación y la información del usuario actualmente
 *     conectado.
 * <>
 *     Endpoints:
 *     - GET /api/v1/auth/me: Devuelve el perfil completo del
 *       usuario interno (de la BD MySQL) que ha iniciado sesión.
 * <>
 * Colabora con {@link com.femt.inventory_management.service.seguridad.imp.CustomOidcUserService}
 * que es el servicio que primero procesa el login y sincroniza
 * al usuario en la base de datos.
 * <>
 * @author  MenesesTech
 * @version 1.0
 * @since   2025-10
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final SegUsuarioRepository segUsuarioRepository;

    public AuthController(SegUsuarioRepository segUsuarioRepository) {
        this.segUsuarioRepository = segUsuarioRepository;
    }
    /**
     * =======================================================
     * Obtener Perfil de Usuario Autenticado
     * =======================================================
     * <>
     * Endpoint protegido que devuelve la información del usuario
     * autenticado, tal como está guardada en la base de datos
     * interna (MySQL).
     * <>
     * Flujo de ejecución:
     * 1. Spring Security intercepta la petición.
     * 2. Valida que el usuario esté autenticado (tenga un JWT/Sesión activa).
     * 3. Inyecta el {@link OidcUser} (que fue procesado por CustomOidcUserService)
     * en el parámetro del método.
     * 4. Extraemos el 'auth0Id' del OidcUser.
     * 5. Buscamos en nuestro SegUsuarioRepository usando ese auth0Id.
     * 6. Devolvemos el {@link Usuario} interno con su rol, estado de activo, etc.
     * <>
     * @param oidcUser El objeto del principal de seguridad OIDC, inyectado por Spring.
     * @return Un ResponseEntity con el objeto Usuario interno o un 404 si no se encuentra.
     */
    @GetMapping("/me")
    public ResponseEntity<Usuario> getUsuarioActual(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser == null) {
            // Esto no debería pasar si la seguridad está bien configurada,
            // pero es una buena validación.
            return ResponseEntity.status(401).build(); // 401 Unauthorized
        }

        // 1. Obtenemos el auth0Id (el 'subject') del usuario autenticado
        String auth0Id = oidcUser.getSubject();

        // 2. Buscamos en NUESTRA base de datos (MySQL)
        return segUsuarioRepository.findByAuth0Id(auth0Id)
                .map(ResponseEntity::ok) // Si se encuentra, devuelve 200 OK con el usuario
                .orElse(ResponseEntity.notFound().build()); // Si no, devuelve 404 Not Found
    }
}
