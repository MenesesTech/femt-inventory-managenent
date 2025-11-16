package com.femt.inventory_management.dto.request;

// Representa cada linea de combinación (Ejm: 25/26, Tira Azul, Planta Verde)
public record KitSerieItemRequestDTO(
        Integer idTalla,
        Integer idColorTira,
        Integer idColorPlanta
) {}
