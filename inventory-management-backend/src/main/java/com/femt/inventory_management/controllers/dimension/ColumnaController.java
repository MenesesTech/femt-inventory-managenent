package com.femt.inventory_management.controllers.dimension;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.service.dimension.imp.ColumnaService;

import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de gestionar la dimensión Columna.
 * Expone los endpoints necesarios para la administración de columnas dentro del sistema.
 *
 * Cada columna representa una unidad organizacional o física dentro de la estructura
 * de inventario implementada.
 *
 * Este controlador delega la lógica al servicio respectivo y garantiza:
 * - Registro de actividad mediante logs.
 * - Validación de datos de entrada.
 * - Respuestas HTTP estandarizadas.
 */
@Slf4j
@RestController
@RequestMapping("/api/dimension/columna")
public class ColumnaController {

    private final ColumnaService columnaService;

    public ColumnaController(ColumnaService columnaService) {
        this.columnaService = columnaService;
    }

    /**
     * Crea múltiples columnas.
     *
     * @param dto listado de columnas a registrar.
     * @return lista de columnas creadas.
     */
    @PostMapping("/crear")
    public ResponseEntity<List<DimensionResponseDTO>> crear(
            @Valid @RequestBody DimensionBatchRequestDTO dto) {

        log.info("Solicitud recibida para crear {} columnas",
                dto.dimensiones() != null ? dto.dimensiones().size() : 0);

        List<DimensionResponseDTO> response = columnaService.crear(dto);

        log.debug("Columnas creadas exitosamente: {}", response.size());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza una columna existente por su ID.
     *
     * @param id identificador único.
     * @param dto datos nuevos.
     * @return columna actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DimensionRequestDTO dto) {

        log.info("Solicitud recibida para actualizar columna {}", id);

        DimensionResponseDTO response = columnaService.actualizar(id, dto);

        log.debug("Columna {} actualizada exitosamente", id);

        return ResponseEntity.ok(response);
    }

    /**
     * Elimina una columna por su ID.
     *
     * @param id identificador único.
     * @return respuesta vacía con estado 204.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        log.warn("Solicitud de eliminación para columna {}", id);

        columnaService.eliminar(id);

        log.info("Columna {} eliminada correctamente", id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Busca una columna por su ID.
     *
     * @param id identificador único.
     * @return columna encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> buscarPorId(@PathVariable Integer id) {

        log.info("Buscando columna por ID {}", id);

        DimensionResponseDTO response = columnaService.buscarPorId(id);

        log.debug("Columna encontrada: {}", response);

        return ResponseEntity.ok(response);
    }

    /**
     * Lista todas las columnas registradas.
     *
     * @return lista completa de columnas.
     */
    @GetMapping
    public ResponseEntity<List<DimensionResponseDTO>> listarColumnas() {

        log.info("Solicitud para listar todas las columnas");

        List<DimensionResponseDTO> response = columnaService.listarTodo();

        log.debug("Total de columnas encontradas: {}", response.size());

        return ResponseEntity.ok(response);
    }
}
