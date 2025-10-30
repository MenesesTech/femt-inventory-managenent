package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.ventas.VentasVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentasVentaRepository extends JpaRepository<VentasVenta, Integer> {
}
