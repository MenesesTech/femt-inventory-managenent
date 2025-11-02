package com.femt.inventory_management.dto.request;

// DTO para solicitar un ensamblaje de componentes
public record EnsamblajeRequestDTO(
        Integer idCategoria,
        Integer idModelo,
        Integer idTalla,
        Integer idColorPlanta,
        Integer idColorTira,
        Integer cantidadAEnsamblar
) {}
