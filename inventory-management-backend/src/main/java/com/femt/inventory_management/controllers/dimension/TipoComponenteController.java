package com.femt.inventory_management.controllers.dimension;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.service.dimension.imp.TipoService;

import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de gestionar la dimensión Tipo de Componente.
 * Provee endpoints CRUD para administrar los tipos utilizados dentro del sistema.
 *
 * Este controlador:
 * - Registra actividad mediante logs.
 * - Valida datos entrantes.
 * - Delegar la lógica de negocio al servicio.
 * - Genera respuestas HTTP estandarizadas.
 */
@Slf4j
@RestController
@RequestMapping("/api/dimension/tipo-componente")
public class TipoComponenteController {

    private final TipoService tipoService;

    public TipoComponenteController(TipoService tipoService) {
        this.tipoService = tipoService;
    }

    /**
     * Crea múltiples tipos de componentes.
     *
     * @param dto lote con los tipos a registrar.
     * @return lista de tipos registrados.
     */
    @PostMapping("/crear")
    public ResponseEntity<List<DimensionResponseDTO>> crear(
            @Valid @RequestBody DimensionBatchRequestDTO dto) {

        log.info("Solicitud para crear {} tipos de componente",
                dto.dimensiones() != null ? dto.dimensiones().size() : 0);

        List<DimensionResponseDTO> response = tipoService.crear(dto);

        log.debug("Tipos de componente creados: {}", response.size());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza un tipo de componente existente.
     *
     * @param id identificador único del tipo.
     * @param dto datos actualizados.
     * @return tipo actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DimensionRequestDTO dto) {

        log.info("Solicitud para actualizar tipo de componente {}", id);

        DimensionResponseDTO response = tipoService.actualizar(id, dto);

        log.debug("Tipo de componente {} actualizado correctamente", id);

        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un tipo de componente por su identificador.
     *
     * @param id identificador único.
     * @return respuesta sin contenido.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        log.warn("Solicitud para eliminar tipo de componente {}", id);

        tipoService.eliminar(id);

        log.info("Tipo de componente {} eliminado exitosamente", id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene un tipo de componente por su ID.
     *
     * @param id identificador único.
     * @return tipo encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> buscarPorId(@PathVariable Integer id) {

        log.info("Buscando tipo de componente {}", id);

        DimensionResponseDTO response = tipoService.buscarPorId(id);

        log.debug("Tipo encontrado: {}", response);

        return ResponseEntity.ok(response);
    }

    /**
     * Lista todos los tipos de componentes registrados.
     *
     * @return lista de tipos.
     */
    @GetMapping
    public ResponseEntity<List<DimensionResponseDTO>> listarTipos() {

        log.info("Solicitud para listar todos los tipos de componentes");

        List<DimensionResponseDTO> response = tipoService.listarTodo();

        log.debug("Total de tipos encontrados: {}", response.size());

        return ResponseEntity.ok(response);
    }
}
