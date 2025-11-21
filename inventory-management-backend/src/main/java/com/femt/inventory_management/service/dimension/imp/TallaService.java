package com.femt.inventory_management.service.dimension.imp;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.exceptions.DimensionNotFoundException;
import com.femt.inventory_management.exceptions.DimensionValidationException;
import com.femt.inventory_management.mapper.dimension.DimensionMapper;
import com.femt.inventory_management.models.dimension.DimTalla;
import com.femt.inventory_management.repository.DimTallaRepository;
import com.femt.inventory_management.service.dimension.DimensionService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar la dimensión Talla.
 * Implementación de los métodos CRUD para el manejo de tallas dentro del sistema.
 *
 * Este servicio valida la integridad de los datos, gestiona las reglas
 * de negocio y consolida las operaciones para la administración de tallas.
 *
 * @author
 * @version 1.1
 * @since 2025-11-17
 */
@Slf4j
@Service
public class TallaService implements DimensionService {

    private final DimTallaRepository tallaRepo;
    private final DimensionMapper mapper;

    public TallaService(DimTallaRepository tallaRepo, DimensionMapper mapper) {
        this.tallaRepo = tallaRepo;
        this.mapper = mapper;
    }

    /**
     * Crea múltiples tallas a partir de un batch DTO.
     *
     * @param batchDTO contiene la lista de tallas a registrar.
     * @return lista de tallas en formato {@link DimensionResponseDTO}.
     * @throws DimensionValidationException si los datos están vacíos o contienen errores.
     */
    @Override
    public List<DimensionResponseDTO> crear(DimensionBatchRequestDTO batchDTO) {

        if (batchDTO.dimensiones() == null || batchDTO.dimensiones().isEmpty()) {
            throw new DimensionValidationException(
                    "La lista de tallas está vacía",
                    "dimensiones"
            );
        }

        log.info("Registrando {} tallas", batchDTO.dimensiones().size());

        List<DimTalla> tallas = new ArrayList<>();

        for (DimensionRequestDTO dto : batchDTO.dimensiones()) {

            if (dto.nombre() == null || dto.nombre().isBlank()) {
                throw new DimensionValidationException(
                        "El nombre de la talla es obligatorio",
                        "nombre"
                );
            }

            if (tallaRepo.existsByNombre(dto.nombre())) {
                throw new DimensionValidationException(
                        "La talla ya existe",
                        "nombre"
                );
            }

            DimTalla talla = new DimTalla();
            talla.setNombre(dto.nombre());

            tallas.add(talla);
        }

        List<DimTalla> guardadas = tallaRepo.saveAll(tallas);

        log.debug("Tallas registradas exitosamente: {}", guardadas.size());

        return guardadas.stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * Actualiza una talla existente.
     *
     * @param id  identificador único de la talla.
     * @param dto datos nuevos para actualizar la talla.
     * @return talla actualizada en formato {@link DimensionResponseDTO}.
     * @throws DimensionNotFoundException si la talla no existe en el sistema.
     */
    @Override
    public DimensionResponseDTO actualizar(Integer id, DimensionRequestDTO dto) {

        log.debug("Actualizando talla con ID {}", id);

        DimTalla talla = tallaRepo.findById(id)
                .orElseThrow(() ->
                        new DimensionNotFoundException("La talla con ID " + id + " no existe")
                );

        talla.setNombre(dto.nombre());

        DimTalla actualizada = tallaRepo.save(talla);

        log.info("Talla {} actualizada correctamente", id);

        return mapper.toDTO(actualizada);
    }

    /**
     * Elimina una talla por su identificador.
     *
     * @param id identificador único de la talla a eliminar.
     * @throws DimensionNotFoundException si la talla no existe.
     */
    @Override
    public void eliminar(Integer id) {

        log.warn("Intentando eliminar talla con ID {}", id);

        if (!tallaRepo.existsById(id)) {
            throw new DimensionNotFoundException("La talla con ID " + id + " no existe");
        }

        tallaRepo.deleteById(id);

        log.info("Talla {} eliminada exitosamente", id);
    }

    /**
     * Busca una talla por su ID.
     *
     * @param id identificador único de la talla.
     * @return talla encontrada en formato {@link DimensionResponseDTO}.
     * @throws DimensionNotFoundException si no se encuentra la talla.
     */
    @Override
    public DimensionResponseDTO buscarPorId(Integer id) {

        log.debug("Buscando talla con ID {}", id);

        DimTalla talla = tallaRepo.findById(id)
                .orElseThrow(() ->
                        new DimensionNotFoundException("La talla con ID " + id + " no existe")
                );

        return mapper.toDTO(talla);
    }

    /**
     * Lista todas las tallas en orden ascendente por ID.
     *
     * @return lista completa de tallas registradas.
     */
    @Override
    public List<DimensionResponseDTO> listarTodo() {

        log.info("Listando todas las tallas...");

        return tallaRepo.findAllByOrderByIdAsc().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
