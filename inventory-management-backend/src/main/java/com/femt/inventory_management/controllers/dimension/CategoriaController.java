package com.femt.inventory_management.controllers.dimension;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.service.dimension.imp.CategoriaService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador REST encargado de gestionar la dimensión Categoría.
 * Expone los endpoints necesarios para realizar operaciones CRUD.
 *
 * Este controlador delega completamente la lógica al servicio correspondiente
 * y se encarga únicamente de manejar las solicitudes HTTP, registrar actividad
 * y estructurar las respuestas.
 */
@Slf4j
@RestController
@RequestMapping("/api/dimension/categoria")
public class CategoriaController {

    private final CategoriaService dimCategoriaService;

    public CategoriaController(CategoriaService dimCategoriaService) {
        this.dimCategoriaService = dimCategoriaService;
    }

    /**
     * Crea múltiples categorías.
     *
     * @param dto batch con la lista de categorías.
     * @return lista de categorías creadas.
     */
    @PostMapping("/crear")
    public ResponseEntity<List<DimensionResponseDTO>> crear(
            @Valid @RequestBody DimensionBatchRequestDTO dto) {

        log.info("Solicitud recibida para crear {} categorías",
                dto.dimensiones() != null ? dto.dimensiones().size() : 0);

        List<DimensionResponseDTO> response = dimCategoriaService.crear(dto);

        log.debug("Categorías creadas exitosamente: {}", response.size());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza una categoría por su ID.
     *
     * @param id identificador de la categoría.
     * @param dto datos actualizados.
     * @return categoría actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DimensionRequestDTO dto) {

        log.info("Solicitud recibida para actualizar categoría {}", id);

        DimensionResponseDTO response = dimCategoriaService.actualizar(id, dto);

        log.debug("Categoría {} actualizada correctamente", id);

        return ResponseEntity.ok(response);
    }

    /**
     * Elimina una categoría por ID.
     *
     * @param id identificador de la categoría.
     * @return respuesta vacía con estado 204.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        log.warn("Solicitud recibida para eliminar categoría {}", id);

        dimCategoriaService.eliminar(id);

        log.info("Categoría {} eliminada correctamente", id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene una categoría por ID.
     *
     * @param id identificador de la categoría.
     * @return categoría encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> buscarPorId(@PathVariable Integer id) {

        log.info("Buscando categoría {}", id);

        DimensionResponseDTO response = dimCategoriaService.buscarPorId(id);

        log.debug("Categoría encontrada: {}", response);

        return ResponseEntity.ok(response);
    }

    /**
     * Lista todas las categorías registradas.
     *
     * @return lista completa de categorías.
     */
    @GetMapping
    public ResponseEntity<List<DimensionResponseDTO>> listarCategorias() {

        log.info("Solicitud para listar todas las categorías");

        List<DimensionResponseDTO> response = dimCategoriaService.listarTodo();

        log.debug("Total de categorías encontradas: {}", response.size());

        return ResponseEntity.ok(response);
    }

}
