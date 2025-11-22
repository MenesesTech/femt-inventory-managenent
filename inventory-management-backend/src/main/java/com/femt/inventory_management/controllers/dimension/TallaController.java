package com.femt.inventory_management.controllers.dimension;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.service.dimension.imp.TallaService;

import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de gestionar la dimensión Talla.
 * Provee endpoints para realizar operaciones CRUD sobre tallas
 * utilizadas dentro de las estructuras o inventarios del sistema.
 *
 * Este controlador se encarga de:
 * - Registrar actividad mediante logs.
 * - Validar los datos entrantes.
 * - Delegar la lógica de negocio al servicio.
 * - Generar respuestas HTTP estandarizadas.
 */
@Slf4j
@RestController
@RequestMapping("/api/dimension/talla")
public class TallaController {

    private final TallaService tallaService;

    public TallaController(TallaService tallaService) {
        this.tallaService = tallaService;
    }

    /**
     * Crea múltiples tallas.
     *
     * @param dto batch con tallas a registrar.
     * @return lista de tallas registradas.
     */
    @PostMapping("/crear")
    public ResponseEntity<List<DimensionResponseDTO>> crear(
            @Valid @RequestBody DimensionBatchRequestDTO dto) {

        log.info("Solicitud para crear {} tallas",
                dto.dimensiones() != null ? dto.dimensiones().size() : 0);

        List<DimensionResponseDTO> response = tallaService.crear(dto);

        log.debug("Tallas creadas exitosamente: {}", response.size());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza una talla existente.
     *
     * @param id identificador de la talla.
     * @param dto datos actualizados.
     * @return talla actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DimensionRequestDTO dto) {

        log.info("Solicitud para actualizar talla {}", id);

        DimensionResponseDTO response = tallaService.actualizar(id, dto);

        log.debug("Talla {} actualizada correctamente", id);

        return ResponseEntity.ok(response);
    }

    /**
     * Elimina una talla por su ID.
     *
     * @param id identificador único.
     * @return respuesta sin contenido.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        log.warn("Solicitud para eliminar talla {}", id);

        tallaService.eliminar(id);

        log.info("Talla {} eliminada exitosamente", id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene una talla por su ID.
     *
     * @param id identificador único.
     * @return talla encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> buscarPorId(@PathVariable Integer id) {

        log.info("Buscando talla {}", id);

        DimensionResponseDTO response = tallaService.buscarPorId(id);

        log.debug("Talla encontrada: {}", response);

        return ResponseEntity.ok(response);
    }

    /**
     * Lista todas las tallas registradas.
     *
     * @return lista completa de tallas.
     */
    @GetMapping
    public ResponseEntity<List<DimensionResponseDTO>> listarTallas() {

        log.info("Solicitud para listar todas las tallas");

        List<DimensionResponseDTO> response = tallaService.listarTodo();

        log.debug("Total de tallas encontradas: {}", response.size());

        return ResponseEntity.ok(response);
    }
}
