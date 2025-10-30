package com.femt.inventory_management.models.dimension;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "dim_tipo_componente")
public class DimTipoComponente extends DimBase{
}
