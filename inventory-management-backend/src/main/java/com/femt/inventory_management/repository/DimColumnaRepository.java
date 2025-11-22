package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.dimension.DimColumna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DimColumnaRepository extends JpaRepository<DimColumna, Integer> {
    boolean existsByNombre(String nombre);
    List<DimColumna> findAllByOrderByIdAsc();
}
