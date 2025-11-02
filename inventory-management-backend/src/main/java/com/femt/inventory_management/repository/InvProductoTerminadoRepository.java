package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.inventario.InventarioProductoTerminado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Busca un SKU específico en inventario
 */
@Repository
public interface InvProductoTerminadoRepository extends JpaRepository<InventarioProductoTerminado, Integer> {
    /**
     * Busca un SKU específico en inventario
     */
    Optional<InventarioProductoTerminado> findByCategoria_IdAndModelo_IdAndTalla_IdAndColorPlanta_IdAndColorTira_Id(
            Integer idCategoria,
            Integer idModelo,
            Integer idTalla,
            Integer idColorPlanta,
            Integer idColorTira
    );
}
