package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.dimension.DimCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimCategoriaRepository extends JpaRepository<DimCategoria, Integer> {
}
