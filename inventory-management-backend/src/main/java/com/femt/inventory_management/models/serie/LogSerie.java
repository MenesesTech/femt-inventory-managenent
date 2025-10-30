package com.femt.inventory_management.models.serie;

import com.femt.inventory_management.models.dimension.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "log_serie")
public class LogSerie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false, foreignKey = @ForeignKey(name = "dim_categoria_log_serie_fk"))
    private DimCategoria dimCategoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modelo", nullable = false,foreignKey = @ForeignKey(name = "dim_modelo_log_serie_fk"))
    private DimModelo dimModelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_serie", nullable = false, foreignKey = @ForeignKey(name = "dim_serie_log_serie_fk"))
    private DimSerie dimSerie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_talla", nullable = false, foreignKey = @ForeignKey(name = "dim_talla_log_serie_fk"))
    private DimTalla dimTalla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_color", nullable = false, foreignKey = @ForeignKey(name = "dim_color_log_serie_fk"))
    private DimColor dimColor;
}
