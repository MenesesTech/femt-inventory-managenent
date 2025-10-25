package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.produccion.ParteProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProParteProduccionRepository extends JpaRepository<ParteProducto, Integer> {
}
