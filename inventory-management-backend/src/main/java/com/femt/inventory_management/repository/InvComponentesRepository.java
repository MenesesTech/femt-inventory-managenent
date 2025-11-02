package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.inventario.InventarioComponentes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvComponentesRepository extends JpaRepository<InventarioComponentes, Integer> {
    /**
     * Buscar un SKU especifico en el inventario
     */
    Optional<InventarioComponentes> findByCategoria_IdAndModelo_IdAndTipoComponente_IdAndTalla_IdAndColor_Id(
            Integer idCategoria,
            Integer idModelo,
            Integer idTipoComponente,
            Integer idTalla,
            Integer idColor
    );
}
