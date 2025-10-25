package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.produccion.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProCategoriaProductoRepository extends JpaRepository<CategoriaProducto, Integer> {
}
