package com.femt.inventory_management.models.kit;

import com.femt.inventory_management.models.dimension.*;
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
@Table(name = "kit_serie_color")
public class KitSerieColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fila", nullable = false)
    private DimFila fila;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_columna", nullable = false)
    private DimColumna columna;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modelo", nullable = false)
    private DimModelo modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_color", nullable = false)
    private DimColor color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private DimCategoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo", nullable = false)
    private DimTipoComponente tipoComponente;
}
