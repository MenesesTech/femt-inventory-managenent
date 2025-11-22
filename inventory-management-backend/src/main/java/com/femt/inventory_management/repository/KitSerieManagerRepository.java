package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.kit.KitSerieColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KitSerieManagerRepository extends JpaRepository<KitSerieColor, Integer> {

    @Query(value = "SELECT * FROM kit_serie " +
            "WHERE id_serie_code = :idSerieCode " +
            "AND id_modelo = :idModelo " +
            "AND id_talla = :idTalla " +
            "AND id_color = :idColor " +
            "AND id_categoria = :idCategoria " +
            "AND id_tipo = :idTipoComponente",
            nativeQuery = true)
    Optional<KitSerieColor> buscarPorModeloTallaColorCategoriaTipoComponenteSerieCode(
            @Param("idSerieCode") Integer idSerieCode,
            @Param("idModelo") Integer idModelo,
            @Param("idTalla") Integer idTalla,
            @Param("idColor") Integer idColor,
            @Param("idCategoria") Integer idCategoria,
            @Param("idTipoComponente") Integer idTipoComponente
    );

    @Query(value = """
    SELECT ks FROM KitSerie ks
    WHERE ks.modelo.id = :idModelo
      AND ks.categoria.id = :idCategoria
    """,nativeQuery = true)
    List<KitSerieColor> findByModeloAndCategoria(
    @Param("idModelo") Integer idModelo,
    @Param("idCategoria") Integer idCategoria
    );

    @Query(value = """
    SELECT ks FROM KitSerie ks
    WHERE ks.modelo.id = :idModelo
      AND ks.categoria.id = :idCategoria
      AND ks.serieCode.id = :idSerieCode
    ORDER BY ks.talla.id ASC, ks.tipoComponente.id ASC
    """, nativeQuery = true)
    List<KitSerieColor> buscarTablaOrganizada(
            Integer idModelo,
            Integer idCategoria,
            Integer idSerieCode
    );
}
