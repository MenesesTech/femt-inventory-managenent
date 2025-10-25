package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.ventas.ComprobantePago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VntComprobantePagoRepository extends JpaRepository<ComprobantePago, Integer> {
}
