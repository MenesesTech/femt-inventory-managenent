package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.dimension.DimCategoria;
import com.femt.inventory_management.models.dimension.DimModelo;
import com.femt.inventory_management.models.dimension.DimTipoComponente;
import com.femt.inventory_management.models.kit.KitSerieColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitSerieColorRepository extends JpaRepository<KitSerieColor, Integer> {
    List<KitSerieColor> findByModeloAndCategoriaAndTipoComponente(
            DimModelo modelo,
            DimCategoria categoria,
            DimTipoComponente tipo
    );

    void deleteByModeloAndCategoriaAndTipoComponente(
            DimModelo modelo,
            DimCategoria categoria,
            DimTipoComponente tipo
    );
}
