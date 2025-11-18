package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.dimension.DimModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DimModeloRepository extends JpaRepository<DimModelo, Integer> {
    boolean existsByNombre(String nombre);
    List<DimModelo> findAllByOrderByIdAsc();
}
