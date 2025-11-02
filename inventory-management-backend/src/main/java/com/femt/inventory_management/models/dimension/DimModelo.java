package com.femt.inventory_management.models.dimension;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa el modelo de la sandalia. Ejm: "Capellada", "Ojota", etc.
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
@Table(name = "dim_modelo")
public class DimModelo extends DimBase{
}
