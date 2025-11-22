package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.inventario.InventarioSandalias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioSandaliasRepository extends JpaRepository<InventarioSandalias, Integer> {

}
