package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.dimension.DimColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DimColorRepository extends JpaRepository<DimColor, Integer> {
    boolean existsByNombreAndCodeRgb(String nombre, String codeRgb);
    List<DimColor> findAllByOrderByIdAsc();
}
