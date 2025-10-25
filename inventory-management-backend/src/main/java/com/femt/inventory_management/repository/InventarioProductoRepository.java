package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.inventario.InventarioProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioProductoRepository extends JpaRepository<InventarioProducto, Integer> {
}
