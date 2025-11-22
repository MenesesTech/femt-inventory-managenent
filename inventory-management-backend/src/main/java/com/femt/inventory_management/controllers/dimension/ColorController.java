package com.femt.inventory_management.controllers.dimension;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.service.dimension.imp.ColorService;

import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de gestionar la dimensión Color.
 * Permite realizar operaciones CRUD sobre los colores registrados
 * en el sistema. Cada color contiene un nombre y un código RGB único.
 *
 * Este controlador delega toda la lógica de negocio al servicio
 * correspondiente y se encarga únicamente de manejar las solicitudes HTTP,
 * validar datos de entrada, registrar actividad y estructurar respuestas.
 */
@Slf4j
@RestController
@RequestMapping("/api/dimension/color")
public class ColorController {

    private final ColorService colorService;

    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    /**
     * Crea múltiples colores a partir de un batch DTO.
     *
     * @param dto batch con la lista de colores.
     * @return lista de colores creados.
     */
    @PostMapping("/crear")
    public ResponseEntity<List<DimensionResponseDTO>> crear(
            @Valid @RequestBody DimensionBatchRequestDTO dto) {

        log.info("Solicitud recibida para crear {} colores",
                dto.dimensiones() != null ? dto.dimensiones().size() : 0);

        List<DimensionResponseDTO> response = colorService.crear(dto);

        log.debug("Colores creados exitosamente: {}", response.size());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza un color por su ID.
     *
     * @param id identificador del color.
     * @param dto datos nuevos.
     * @return color actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DimensionRequestDTO dto) {

        log.info("Solicitud para actualizar color {}", id);

        DimensionResponseDTO response = colorService.actualizar(id, dto);

        log.debug("Color {} actualizado correctamente", id);

        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un color por su ID.
     *
     * @param id identificador único.
     * @return respuesta vacía con estado 204.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        log.warn("Solicitud de eliminación para el color {}", id);

        colorService.eliminar(id);

        log.info("Color {} eliminado exitosamente", id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene un color por su ID.
     *
     * @param id identificador único.
     * @return color encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> buscarPorId(@PathVariable Integer id) {

        log.info("Buscando color {}", id);

        DimensionResponseDTO response = colorService.buscarPorId(id);

        log.debug("Color encontrado: {}", response);

        return ResponseEntity.ok(response);
    }

    /**
     * Lista todos los colores registrados.
     *
     * @return lista de colores.
     */
    @GetMapping
    public ResponseEntity<List<DimensionResponseDTO>> listarColores() {

        log.info("Solicitud para listar todos los colores");

        List<DimensionResponseDTO> response = colorService.listarTodo();

        log.debug("Total de colores encontrados: {}", response.size());

        return ResponseEntity.ok(response);
    }
}
