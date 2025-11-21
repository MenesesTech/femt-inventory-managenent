package com.femt.inventory_management.service.dimension.imp;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.exceptions.DimensionNotFoundException;
import com.femt.inventory_management.exceptions.DimensionValidationException;
import com.femt.inventory_management.mapper.dimension.DimensionMapper;
import com.femt.inventory_management.models.dimension.DimColumna;
import com.femt.inventory_management.repository.DimColumnaRepository;
import com.femt.inventory_management.service.dimension.DimensionService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar la dimensión Columna.
 * Implementa las operaciones CRUD para la administración de columnas dentro del sistema.
 *
 * Las columnas representan una dimensión organizacional o física, según el modelo
 * de inventario o estructura implementada en el proyecto.
 *
 * Este servicio centraliza validaciones, reglas de negocio y manejo de
 * excepciones personalizadas relacionadas con esta dimensión.
 *
 * @author
 * @version 1.1
 * @since 2025-11-17
 */
@Slf4j
@Service
public class ColumnaService implements DimensionService {

    private final DimColumnaRepository columnaRepo;
    private final DimensionMapper mapper;

    public ColumnaService(DimColumnaRepository columnaRepo, DimensionMapper mapper) {
        this.columnaRepo = columnaRepo;
        this.mapper = mapper;
    }

    /**
     * Crea múltiples columnas a partir de un batch DTO.
     *
     * @param batchDTO contiene la lista de columnas a registrar.
     * @return lista de columnas en formato {@link DimensionResponseDTO}.
     * @throws DimensionValidationException si los datos son inválidos.
     */
    @Override
    public List<DimensionResponseDTO> crear(DimensionBatchRequestDTO batchDTO) {

        if (batchDTO.dimensiones() == null || batchDTO.dimensiones().isEmpty()) {
            throw new DimensionValidationException(
                    "La lista de columnas está vacía",
                    "dimensiones"
            );
        }

        log.info("Registrando {} columnas", batchDTO.dimensiones().size());

        List<DimColumna> columnas = new ArrayList<>();

        for (DimensionRequestDTO dto : batchDTO.dimensiones()) {

            if (dto.nombre() == null || dto.nombre().isBlank()) {
                throw new DimensionValidationException(
                        "El nombre de la columna es obligatorio",
                        "nombre"
                );
            }

            if (columnaRepo.existsByNombre(dto.nombre())) {
                throw new DimensionValidationException(
                        "La columna ya existe",
                        "nombre"
                );
            }

            DimColumna columna = new DimColumna();
            columna.setNombre(dto.nombre());

            columnas.add(columna);
        }

        List<DimColumna> guardadas = columnaRepo.saveAll(columnas);

        log.debug("Columnas registradas exitosamente: {}", guardadas.size());

        return guardadas.stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * Actualiza una columna existente.
     *
     * @param id  identificador único de la columna.
     * @param dto datos nuevos para actualizar la columna.
     * @return columna actualizada en formato {@link DimensionResponseDTO}.
     * @throws DimensionNotFoundException si la columna no existe.
     */
    @Override
    public DimensionResponseDTO actualizar(Integer id, DimensionRequestDTO dto) {

        log.debug("Actualizando columna con ID {}", id);

        DimColumna columna = columnaRepo.findById(id)
                .orElseThrow(() ->
                        new DimensionNotFoundException("La columna con ID " + id + " no existe")
                );

        columna.setNombre(dto.nombre());

        DimColumna actualizada = columnaRepo.save(columna);

        log.info("Columna {} actualizada correctamente", id);

        return mapper.toDTO(actualizada);
    }

    /**
     * Elimina una columna por su identificador.
     *
     * @param id identificador único de la columna a eliminar.
     * @throws DimensionNotFoundException si la columna no existe.
     */
    @Override
    public void eliminar(Integer id) {

        log.warn("Intentando eliminar columna con ID {}", id);

        if (!columnaRepo.existsById(id)) {
            throw new DimensionNotFoundException("La columna con ID " + id + " no existe");
        }

        columnaRepo.deleteById(id);

        log.info("Columna {} eliminada exitosamente", id);
    }

    /**
     * Busca una columna por su ID.
     *
     * @param id identificador único.
     * @return columna encontrada en formato {@link DimensionResponseDTO}.
     * @throws DimensionNotFoundException si no existe la columna.
     */
    @Override
    public DimensionResponseDTO buscarPorId(Integer id) {

        log.debug("Buscando columna con ID {}", id);

        DimColumna columna = columnaRepo.findById(id)
                .orElseThrow(() ->
                        new DimensionNotFoundException("La columna con ID " + id + " no existe")
                );

        return mapper.toDTO(columna);
    }

    /**
     * Lista todas las columnas registradas en orden ascendente por ID.
     *
     * @return lista de columnas registradas.
     */
    @Override
    public List<DimensionResponseDTO> listarTodo() {

        log.info("Listando todas las columnas...");

        return columnaRepo.findAllByOrderByIdAsc().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
