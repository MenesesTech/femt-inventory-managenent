package com.femt.inventory_management.models.kit;

import com.femt.inventory_management.models.dimension.DimModelo;
import com.femt.inventory_management.models.dimension.DimTalla;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kit_serie_manager")
public class KitSerieManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_kit_serie_color", nullable = false)
    private KitSerieColor kitSerieColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modelo", nullable = false)
    private DimModelo dimModelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_talla", nullable = false)
    private DimTalla dimTalla;

    @Column(name = "url_image", length = 50, nullable = true)
    private String urlImage;
}
