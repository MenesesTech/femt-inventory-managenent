package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.dimension.DimCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DimCategoriaRepository extends JpaRepository<DimCategoria, Integer> {
    boolean existsByNombre(String nombre);
    List<DimCategoria> findAllByOrderByIdAsc();
}
