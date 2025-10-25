package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.produccion.TallaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProTallaProductoRepository extends JpaRepository<TallaProducto, Integer> {
}
