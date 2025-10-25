package com.femt.inventory_management.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

/**
 * =========================================
 * Config de Seguridad Principal del Sistema
 * =========================================
 * <>
 *     Esta clase define la config de seguridad global de la app,
 *     integrando la autenticación mediante OpenID Connect (OIDC) con Auth0
 *     como proveedor de identidad. Además, establece las políticas de autorización,
 *     manejo de sesiones, control de CSRF y cierre de sesión.
 *     <>
 *         Responsabilidades:
 *         - Proteger rutas y endpoints según autenticación
 *         - Configurar el flujo de inicio de sesión mediante OAuth2 / OIDC
 *         - Implementar manejadores personalizados de errores de autenticación
 *         - Gestionar el proceso de logout contra Auth0 (incluyendo redirección segura)
 *     <>
 * Esta Configuración funciona junto con {@link com.femt.inventory_management.service.seguridad.imp.CustomOidcUserService}
 * el cual gestiona la carga y validación del usuario autenticado desde Auth0
 * dentro del contexto interno del sistema
 * <>
 *
 * @author MenesesTech
 * @version 1.0
 * @since 2025-10
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Value("${spring.security.oauth2.client.provider.auth0.issuer-uri}") // Url base proveedor Auth0
    private String issuer;
    @Value("${spring.security.oauth2.client.registration.auth0.client-id}") // Id Cliente registrado en Auth0
    private String clientId;

    /**
     * =====================================================
     * Filtro principal de seguridad (Security Filter Chain)
     * =====================================================
     * <>
     *     Configura las políticas de seguridad HTTP, autenticación y autorización de Spring Security
     *     <>
     *         Configuración general:
     *         - CORS: Habilitado con la configuración por defecto
     *         - CSRF: protegido mediante cookie (CookieCsrfTokenRepository)
     *         - Autorización: acceso público solo a la raíz "/", el resto requiere autenticación
     *         - OAuth2 login: flujo de autenticación OpenID Connect con manejo de errores personalizado
     *         - Logout: Implementa un cierre de sesión coordinado con Auth0
     *     <>
     * @param http instancia del builder de configuración de seguridad HTTP
     * @return el filtro de seguridad configurado
     * @throws Exception si ocurre un error durante la construccion de la cadena de filtros
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                )
                .oauth2Login(oauth2 -> oauth2
//                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(this.customOidcUserService))
                        .failureHandler(authenticationFailureHandler())
                )
                .logout(logout -> logout
                        .addLogoutHandler(logoutHandler()));
        return http.build();
    }

    /**
     * ===================================================
     * Gestor de fallos de autenticación (Login Errors)
     * ===================================================
     * <>
     *     Define el comportamiento del sistema cunado un proceso de autenticación
     *     con Auth0 falla por alguna razón. Puede deberse a:
     *     <>
     *         - Cuenta de usuario desactivado o pendiente de aprobación interna
     *         - Token inválido o error durante el intercambio
     *         - Cancelación del login por porte del usuario
     *     <>
     * El gestor redirige al usuario a una ruta específica en función del tipo de excepción
     * <>
     *     - DisabledException: acceso-pendiente
     *     - Otros errores: error-login
     * <>
     * Esta implementación se vincula al flujo de login definido en {@link #configure(HttpSecurity)}.
     *
     * @return instancia de {@link AuthenticationFailureHandler} personalizada
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            String redirectUrl;
            if (exception instanceof DisabledException) {
                // Si la cuenta está pendiente, redirige a una URL específica
                redirectUrl = "/acceso-pendiente";
            } else {
                // Para cualquier otro error de login
                redirectUrl = "/error-login";
            }
            response.sendRedirect(redirectUrl);
        };
    }

    /**
     * =====================================================
     * Gestor de Logout Coordinado (Auth0 + Local)
     * =====================================================
     * <>
     * Este gestor extiende el proceso estándar de logout de Spring Security
     * para incluir el cierre de sesión en Auth0.
     * <>
     * Cuando el usuario cierra sesión en la aplicación:
     * <>
     *     - Spring invalida la sesión local y limpia el contexto de seguridad<>
     *     - Se redirige al endpoint {/v2/logout} de Auth0
     *     - Auth0 finaliza la sesión del usuario en el proveedor de identidad
     *     - El usuario es redirigido de vuelta al dominio base de la app (baseUrl)
     * <>
     * @return instancia de {@link LogoutHandler} que implementa la redirección segura hacia Auth0
     */
    private LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {
            try {
                // Construye la URL base del sistema (ej. http://localhost:3000)
                String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

                // Redirige al endpoint de logout de Auth0 con el returnTo configurado
                response.sendRedirect(issuer + "v2/logout?client_id="+clientId+"&returnTo="+baseUrl);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        };
    }
}