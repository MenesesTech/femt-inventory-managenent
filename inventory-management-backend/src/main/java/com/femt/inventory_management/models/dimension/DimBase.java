package com.femt.inventory_management.models.dimension;

import jakarta.persistence.*;
import lombok.*;

/**
 * Se utiliza @MappedSuperclass para proporcionar atributos comunes (ID y Nombre)
 * a todas las dimensiones, con el objetivo de duplicar código
 * - Ojo: Los repositorios se crean para las clases hijas, no para esta.
 *
 * @author menesesTech
 * @version 1.0
 * @since 2025-11-01
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class DimBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Representa el nombre de cada dimensión
     * - DimCategoria(nombre): "Niños", "Niñas", "Adultos".
     * - DimColor(nombre): "Rojo", "Azul", "Negro", etc.
     * - DimModelo(nombre): "Capellada", "Ojota", etc.
     * - DimSerie(nombre): "A", "B", "C", etc.
     * - DimTalla(nombre): "25/26", "24/23", etc.
     * - DimTipoComponente(nombre): "Planta", "Tira".
     */
    @NonNull
    @Column(nullable = false, length = 20)
    private String nombre;

}
