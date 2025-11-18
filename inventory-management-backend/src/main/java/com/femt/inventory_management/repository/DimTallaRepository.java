package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.dimension.DimTalla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DimTallaRepository extends JpaRepository<DimTalla, Integer> {
    boolean existsByNombre(String nombre);
    List<DimTalla> findAllByOrderByIdAsc();
}
