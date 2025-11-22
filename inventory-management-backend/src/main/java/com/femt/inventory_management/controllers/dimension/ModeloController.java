package com.femt.inventory_management.controllers.dimension;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.service.dimension.imp.ModeloService;

import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de gestionar la dimensión Modelo.
 * Provee endpoints CRUD para la administración de modelos utilizados
 * dentro del inventario o estructuras relacionadas del sistema.
 *
 * Este controlador se encarga de:
 * - Registrar actividad mediante logs.
 * - Validar datos recibidos.
 * - Delegar la lógica de negocio al servicio.
 * - Estandarizar las respuestas HTTP enviadas.
 */
@Slf4j
@RestController
@RequestMapping("/api/dimension/modelo")
public class ModeloController {

    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    /**
     * Crea múltiples modelos a partir de un batch DTO.
     *
     * @param dto lote con los modelos a registrar.
     * @return lista de modelos registrados.
     */
    @PostMapping("/crear")
    public ResponseEntity<List<DimensionResponseDTO>> crear(
            @Valid @RequestBody DimensionBatchRequestDTO dto) {

        log.info("Solicitud recibida para crear {} modelos",
                dto.dimensiones() != null ? dto.dimensiones().size() : 0);

        List<DimensionResponseDTO> response = modeloService.crear(dto);

        log.debug("Modelos creados exitosamente: {}", response.size());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza un modelo existente.
     *
     * @param id identificador único del modelo.
     * @param dto datos actualizados.
     * @return modelo actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DimensionRequestDTO dto) {

        log.info("Solicitud para actualizar modelo {}", id);

        DimensionResponseDTO response = modeloService.actualizar(id, dto);

        log.debug("Modelo {} actualizado correctamente", id);

        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un modelo por su ID.
     *
     * @param id identificador único.
     * @return respuesta vacía con estado 204.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        log.warn("Solicitud para eliminar modelo {}", id);

        modeloService.eliminar(id);

        log.info("Modelo {} eliminado exitosamente", id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene un modelo por su ID.
     *
     * @param id identificador único.
     * @return modelo encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> buscarPorId(@PathVariable Integer id) {

        log.info("Buscando modelo {}", id);

        DimensionResponseDTO response = modeloService.buscarPorId(id);

        log.debug("Modelo encontrado: {}", response);

        return ResponseEntity.ok(response);
    }

    /**
     * Lista todos los modelos registrados.
     *
     * @return lista de modelos.
     */
    @GetMapping
    public ResponseEntity<List<DimensionResponseDTO>> listarModelos() {

        log.info("Solicitud para listar todos los modelos");

        List<DimensionResponseDTO> response = modeloService.listarTodo();

        log.debug("Total de modelos encontrados: {}", response.size());

        return ResponseEntity.ok(response);
    }
}
