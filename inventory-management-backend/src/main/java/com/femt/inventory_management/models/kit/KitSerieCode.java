package com.femt.inventory_management.models.kit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kit_serie_code")
public class KitSerieCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "serie_code", nullable = false, length = 20)
    private String serieCode;

    @Column(name = "serie_letra", nullable = false, length = 1)
    private String serieLetra;

    @Column(name = "orden", nullable = false)
    private Integer orden;

}
