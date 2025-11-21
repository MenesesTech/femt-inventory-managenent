package com.femt.inventory_management.service.dimension.imp;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.exceptions.DimensionNotFoundException;
import com.femt.inventory_management.exceptions.DimensionValidationException;
import com.femt.inventory_management.mapper.dimension.DimensionMapper;
import com.femt.inventory_management.models.dimension.DimModelo;
import com.femt.inventory_management.repository.DimModeloRepository;
import com.femt.inventory_management.service.dimension.DimensionService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar la dimensión Modelo.
 * Implementación de los métodos CRUD para el manejo de modelos dentro del sistema.
 *
 * Este servicio administra la creación, actualización, eliminación y consulta
 * de modelos, aplicando validaciones y reglas de negocio correspondientes.
 *
 * @author
 * @version 1.1
 * @since 2025-11-17
 */
@Slf4j
@Service
public class ModeloService implements DimensionService {

    private final DimModeloRepository modeloRepo;
    private final DimensionMapper mapper;

    public ModeloService(DimModeloRepository modeloRepo, DimensionMapper mapper) {
        this.modeloRepo = modeloRepo;
        this.mapper = mapper;
    }

    /**
     * Crea múltiples modelos a partir de un batch DTO.
     *
     * @param batchDTO contiene la lista de modelos a registrar.
     * @return lista de modelos en formato {@link DimensionResponseDTO}.
     * @throws DimensionValidationException si los datos no son válidos o la lista está vacía.
     */
    @Override
    public List<DimensionResponseDTO> crear(DimensionBatchRequestDTO batchDTO) {

        if (batchDTO.dimensiones() == null || batchDTO.dimensiones().isEmpty()) {
            throw new DimensionValidationException(
                    "La lista de modelos está vacía",
                    "dimensiones"
            );
        }

        log.info("Registrando {} modelos", batchDTO.dimensiones().size());

        List<DimModelo> modelos = new ArrayList<>();

        for (DimensionRequestDTO dto : batchDTO.dimensiones()) {

            if (dto.nombre() == null || dto.nombre().isBlank()) {
                throw new DimensionValidationException(
                        "El nombre del modelo es obligatorio",
                        "nombre"
                );
            }

            if (modeloRepo.existsByNombre(dto.nombre())) {
                throw new DimensionValidationException(
                        "El modelo ya existe",
                        "nombre"
                );
            }

            DimModelo modelo = new DimModelo();
            modelo.setNombre(dto.nombre());

            modelos.add(modelo);
        }

        List<DimModelo> guardados = modeloRepo.saveAll(modelos);

        log.debug("Modelos registrados exitosamente: {}", guardados.size());

        return guardados.stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * Actualiza un modelo existente.
     *
     * @param id  identificador único del modelo.
     * @param dto datos nuevos para actualizar el modelo.
     * @return modelo actualizado en formato {@link DimensionResponseDTO}.
     * @throws DimensionNotFoundException si el modelo no existe.
     */
    @Override
    public DimensionResponseDTO actualizar(Integer id, DimensionRequestDTO dto) {

        log.debug("Actualizando modelo con ID {}", id);

        DimModelo modelo = modeloRepo.findById(id)
                .orElseThrow(() ->
                        new DimensionNotFoundException("El modelo con ID " + id + " no existe")
                );

        modelo.setNombre(dto.nombre());

        DimModelo actualizado = modeloRepo.save(modelo);

        log.info("Modelo {} actualizado correctamente", id);

        return mapper.toDTO(actualizado);
    }

    /**
     * Elimina un modelo por su identificador.
     *
     * @param id identificador del modelo a eliminar.
     * @throws DimensionNotFoundException si el modelo no existe.
     */
    @Override
    public void eliminar(Integer id) {

        log.warn("Intentando eliminar modelo con ID {}", id);

        if (!modeloRepo.existsById(id)) {
            throw new DimensionNotFoundException(
                    "El modelo con ID " + id + " no existe"
            );
        }

        modeloRepo.deleteById(id);

        log.info("Modelo {} eliminado exitosamente", id);
    }

    /**
     * Busca un modelo por su ID.
     *
     * @param id identificador único del modelo.
     * @return modelo encontrado en formato {@link DimensionResponseDTO}.
     * @throws DimensionNotFoundException si el modelo no existe.
     */
    @Override
    public DimensionResponseDTO buscarPorId(Integer id) {

        log.debug("Buscando modelo con ID {}", id);

        DimModelo modelo = modeloRepo.findById(id)
                .orElseThrow(() ->
                        new DimensionNotFoundException("El modelo con ID " + id + " no existe")
                );

        return mapper.toDTO(modelo);
    }

    /**
     * Lista todos los modelos en orden ascendente por ID.
     *
     * @return lista completa de modelos registrados.
     */
    @Override
    public List<DimensionResponseDTO> listarTodo() {

        log.info("Listando todos los modelos...");

        return modeloRepo.findAllByOrderByIdAsc().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
