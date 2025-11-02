package com.femt.inventory_management.models.dimension;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa la serie de la sandalia. Ejm: "A", "B", "C", etc.
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
@Table(name = "dim_serie")
public class DimSerie extends DimBase{

}
