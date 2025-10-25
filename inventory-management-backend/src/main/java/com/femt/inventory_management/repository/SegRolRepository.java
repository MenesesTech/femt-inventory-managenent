package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.seguridad.Rol;
import com.femt.inventory_management.models.seguridad.enums.RolUsuarioEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SegRolRepository extends JpaRepository<Rol, Integer> {
    /**
     * Busca un rol por su valor de Enum.
     * @param rolUsuario El enum del rol (ej. RolUsuarioEnum.ADMIN).
     * @return Un Optional que contiene el Rol si se encuentra.
     */
    Optional<Rol> findByRolUsuario(RolUsuarioEnum rolUsuario);
}