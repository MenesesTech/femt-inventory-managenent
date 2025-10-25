package com.femt.inventory_management.models.produccion;

import com.femt.inventory_management.models.produccion.enums.ColorProductoEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "produccion_color")
public class ColorProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", nullable = false, length = 20)
    private ColorProductoEnum color;

    @NonNull
    @Column(name = "rgb_color", nullable = false, length = 6)
    private String rgbColor;
}
