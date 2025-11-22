package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.dimension.DimFila;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DimFilaRepository extends JpaRepository<DimFila, Integer> {
    boolean existsByNombre(String nombre);
    List<DimFila> findAllByOrderByIdAsc();
}
