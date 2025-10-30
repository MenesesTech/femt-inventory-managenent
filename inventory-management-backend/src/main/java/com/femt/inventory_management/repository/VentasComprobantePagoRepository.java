package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.ventas.VentasComprobantePago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentasComprobantePagoRepository extends JpaRepository<VentasComprobantePago, Integer> {
}
