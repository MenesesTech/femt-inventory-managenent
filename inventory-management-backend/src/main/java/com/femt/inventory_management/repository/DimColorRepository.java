package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.dimension.DimColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimColorRepository extends JpaRepository<DimColor, Integer> {
}
