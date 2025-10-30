package com.femt.inventory_management.models.dimension;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "dim_color")
public class DimColor extends DimBase{

}
