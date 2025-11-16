package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.dimension.DimTipoComponente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DimTipoComponenteRepository extends JpaRepository<DimTipoComponente, Integer> {
    Optional<DimTipoComponente> findByNombre(String nombre);
}
