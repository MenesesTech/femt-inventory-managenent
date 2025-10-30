package com.femt.inventory_management.models.dimension;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "dim_talla")
public class DimTalla extends DimBase{
}
