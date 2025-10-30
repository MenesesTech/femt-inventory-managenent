package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.ventas.VentasPedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentasPedidoDetalleRepository extends JpaRepository<VentasPedidoDetalle, Integer> {
}
