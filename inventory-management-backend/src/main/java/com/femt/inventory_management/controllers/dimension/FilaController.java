package com.femt.inventory_management.controllers.dimension;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.service.dimension.imp.FilaService;

import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de gestionar la dimensión Fila.
 * Provee endpoints para realizar operaciones CRUD sobre las filas
 * utilizadas en la estructura organizacional o de inventarios del sistema.
 *
 * Este controlador se encarga de:
 * - Registrar actividad mediante logs.
 * - Validar datos recibidos.
 * - Delegar la lógica al servicio correspondiente.
 * - Estandarizar las respuestas HTTP.
 */
@Slf4j
@RestController
@RequestMapping("/api/dimension/fila")
public class FilaController {

    private final FilaService filaService;

    public FilaController(FilaService filaService) {
        this.filaService = filaService;
    }

    /**
     * Crea múltiples filas.
     *
     * @param dto batch con las filas a registrar.
     * @return lista de filas registradas.
     */
    @PostMapping("/crear")
    public ResponseEntity<List<DimensionResponseDTO>> crear(
            @Valid @RequestBody DimensionBatchRequestDTO dto) {

        log.info("Solicitud para crear {} filas",
                dto.dimensiones() != null ? dto.dimensiones().size() : 0);

        List<DimensionResponseDTO> response = filaService.crear(dto);

        log.debug("Filas creadas exitosamente: {}", response.size());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza una fila existente por su ID.
     *
     * @param id identificador único de la fila.
     * @param dto datos nuevos de la fila.
     * @return fila actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DimensionRequestDTO dto) {

        log.info("Solicitud para actualizar fila {}", id);

        DimensionResponseDTO response = filaService.actualizar(id, dto);

        log.debug("Fila {} actualizada correctamente", id);

        return ResponseEntity.ok(response);
    }

    /**
     * Elimina una fila por su ID.
     *
     * @param id identificador único.
     * @return respuesta sin contenido.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        log.warn("Solicitud de eliminación para la fila {}", id);

        filaService.eliminar(id);

        log.info("Fila {} eliminada exitosamente", id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene una fila por su ID.
     *
     * @param id identificador único.
     * @return fila encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> buscarPorId(@PathVariable Integer id) {

        log.info("Buscando fila {}", id);

        DimensionResponseDTO response = filaService.buscarPorId(id);

        log.debug("Fila encontrada: {}", response);

        return ResponseEntity.ok(response);
    }

    /**
     * Lista todas las filas registradas.
     *
     * @return lista completa de filas.
     */
    @GetMapping
    public ResponseEntity<List<DimensionResponseDTO>> listarFilas() {

        log.info("Solicitud para listar todas las filas");

        List<DimensionResponseDTO> response = filaService.listarTodo();

        log.debug("Total de filas encontradas: {}", response.size());

        return ResponseEntity.ok(response);
    }
}
