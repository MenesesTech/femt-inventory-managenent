package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.produccion.MermaMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProMermaMaterialRepository extends JpaRepository<MermaMaterial, Integer> {
}
