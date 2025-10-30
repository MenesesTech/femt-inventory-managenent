package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.dimension.DimSerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimSerieRepository extends JpaRepository<DimSerie, Integer> {
}
