package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.serie.LogSerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogSerieRepository extends JpaRepository<LogSerie, Integer> {
    /**
     * Busca los colores de una serie
     * basada en Categoría, Modelo y Serie.
     */
    List<LogSerie> findByDimCategoria_IdAndDimModelo_IdAndDimSerie_Id(
            Integer idCategoria, Integer idModelo, Integer idSerie
    );
}
