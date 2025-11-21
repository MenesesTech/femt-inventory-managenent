package com.femt.inventory_management.service.dimension.imp;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.exceptions.DimensionNotFoundException;
import com.femt.inventory_management.exceptions.DimensionValidationException;
import com.femt.inventory_management.mapper.dimension.DimensionMapper;
import com.femt.inventory_management.models.dimension.DimFila;
import com.femt.inventory_management.repository.DimFilaRepository;
import com.femt.inventory_management.service.dimension.DimensionService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar la dimensión Fila.
 * Implementa las operaciones CRUD necesarias para administrar filas dentro del sistema.
 *
 * Este servicio valida datos, maneja excepciones personalizadas y ofrece
 * operaciones centralizadas para la administración de filas en estructuras
 * organizacionales, de inventarios u otros componentes del sistema.
 */
@Slf4j
@Service
public class FilaService implements DimensionService {

    private final DimFilaRepository filaRepo;
    private final DimensionMapper mapper;

    public FilaService(DimFilaRepository filaRepo, DimensionMapper mapper) {
        this.filaRepo = filaRepo;
        this.mapper = mapper;
    }

    /**
     * Crea múltiples filas a partir de un batch DTO.
     *
     * @param batchDTO contiene la lista de filas a registrar.
     * @return lista de filas en formato {@link DimensionResponseDTO}.
     * @throws DimensionValidationException si los datos son inválidos o la lista está vacía.
     */
    @Override
    public List<DimensionResponseDTO> crear(DimensionBatchRequestDTO batchDTO) {

        if (batchDTO.dimensiones() == null || batchDTO.dimensiones().isEmpty()) {
            throw new DimensionValidationException(
                    "La lista de filas está vacía",
                    "dimensiones"
            );
        }

        log.info("Registrando {} filas", batchDTO.dimensiones().size());

        List<DimFila> filas = new ArrayList<>();

        for (DimensionRequestDTO dto : batchDTO.dimensiones()) {
            if (dto.nombre() == null || dto.nombre().isBlank()) {
                throw new DimensionValidationException(
                        "El nombre de la fila es obligatorio",
                        "nombre"
                );
            }

            if (filaRepo.existsByNombre(dto.nombre())) {
                throw new DimensionValidationException(
                        "La fila ya existe",
                        "nombre"
                );
            }

            DimFila fila = new DimFila();
            fila.setNombre(dto.nombre());

            filas.add(fila);
        }

        List<DimFila> guardadas = filaRepo.saveAll(filas);

        log.debug("Filas registradas exitosamente: {}", guardadas.size());

        return guardadas.stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * Actualiza una fila existente.
     *
     * @param id  identificador único de la fila.
     * @param dto datos nuevos para actualizar la fila.
     * @return fila actualizada en formato {@link DimensionResponseDTO}.
     * @throws DimensionNotFoundException si la fila no existe en el sistema.
     */
    @Override
    public DimensionResponseDTO actualizar(Integer id, DimensionRequestDTO dto) {

        log.debug("Actualizando fila con ID {}", id);

        DimFila fila = filaRepo.findById(id)
                .orElseThrow(() ->
                        new DimensionNotFoundException("La fila con ID " + id + " no existe")
                );

        fila.setNombre(dto.nombre());

        DimFila actualizada = filaRepo.save(fila);

        log.info("Fila {} actualizada correctamente", id);

        return mapper.toDTO(actualizada);
    }

    /**
     * Elimina una fila por su identificador.
     *
     * @param id identificador único de la fila.
     * @throws DimensionNotFoundException si la fila no existe.
     */
    @Override
    public void eliminar(Integer id) {

        log.warn("Intentando eliminar fila con ID {}", id);

        if (!filaRepo.existsById(id)) {
            throw new DimensionNotFoundException("La fila con ID " + id + " no existe");
        }

        filaRepo.deleteById(id);

        log.info("Fila {} eliminada exitosamente", id);
    }

    /**
     * Busca una fila por su ID.
     *
     * @param id identificador único de la fila.
     * @return fila encontrada en formato {@link DimensionResponseDTO}.
     * @throws DimensionNotFoundException si no existe la fila.
     */
    @Override
    public DimensionResponseDTO buscarPorId(Integer id) {

        log.debug("Buscando fila con ID {}", id);

        DimFila fila = filaRepo.findById(id)
                .orElseThrow(() ->
                        new DimensionNotFoundException("La fila con ID " + id + " no existe")
                );

        return mapper.toDTO(fila);
    }

    /**
     * Lista todas las filas registradas de forma ascendente por ID.
     *
     * @return lista completa de filas registradas.
     */
    @Override
    public List<DimensionResponseDTO> listarTodo() {

        log.info("Listando todas las filas...");

        return filaRepo.findAllByOrderByIdAsc().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
