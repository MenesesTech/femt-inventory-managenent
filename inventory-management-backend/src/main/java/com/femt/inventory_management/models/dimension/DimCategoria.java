package com.femt.inventory_management.models.dimension;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa la categoría de la sandalia. Ejm: "Niños", "Niñas", "Adultos"
 * Esta Entidad hereda campos id y nombre de la clase de {@link DimBase}
 * <>
 * @author MenesesTech
 * @version 1.0
 * @since 2025-11-01
 * @see DimBase
 */
@Entity
@Getter
@Setter
@Table(name = "dim_categoria")
public class DimCategoria extends DimBase{
}
