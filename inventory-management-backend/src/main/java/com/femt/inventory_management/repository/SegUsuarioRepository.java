package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.seguridad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SegUsuarioRepository extends JpaRepository<Usuario, Integer> {
    /**
     * Busca un usuario por su ID único de Auth0.
     * @param auth0Id El ID proporcionado por Auth0 (claim 'sub').
     * @return Un Optional que contiene el Usuario si se encuentra.
     */
    Optional<Usuario> findByAuth0Id(String auth0Id);
}
