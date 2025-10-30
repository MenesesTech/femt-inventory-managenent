package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.dimension.DimTalla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimTallaRepository extends JpaRepository<DimTalla, Integer> {
}
