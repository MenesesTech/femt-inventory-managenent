package com.femt.inventory_management.models.dimension;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "dim_modelo")
public class DimModelo extends DimBase{
}
