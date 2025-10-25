package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.ventas.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VntPedidoRepository extends JpaRepository<Pedido,Integer> {
}
