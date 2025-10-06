package com.femt.inventory_management.model.produccion;

import com.femt.inventory_management.model.produccion.enums.CategoriaProductoEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "produccion_categoria")
public class CategoriaProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name ="nombre", nullable= false, length = 20)
    private CategoriaProductoEnum categoria;
}
