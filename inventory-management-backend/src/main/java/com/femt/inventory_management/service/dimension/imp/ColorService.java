package com.femt.inventory_management.service.dimension.imp;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.exceptions.DimensionNotFoundException;
import com.femt.inventory_management.exceptions.DimensionValidationException;
import com.femt.inventory_management.mapper.dimension.DimensionMapper;
import com.femt.inventory_management.models.dimension.DimColor;
import com.femt.inventory_management.repository.DimColorRepository;
import com.femt.inventory_management.service.dimension.DimensionService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar la dimensión Color.
 * Implementa las operaciones CRUD y las validaciones necesarias para el manejo
 * de colores dentro del sistema.
 *
 * Cada color incluye un nombre y un código RGB único.
 */
@Slf4j
@Service
public class ColorService implements DimensionService {

    private final DimColorRepository colorRepo;
    private final DimensionMapper mapper;

    public ColorService(DimColorRepository colorRepo, DimensionMapper mapper) {
        this.colorRepo = colorRepo;
        this.mapper = mapper;
    }

    /**
     * Crea múltiples colores a partir de un batch DTO.
     *
     * @param batchDTO lista con los colores a registrar.
     * @return lista de colores en formato {@link DimensionResponseDTO}.
     * @throws DimensionValidationException si la lista viene vacía o algún dato no es válido.
     */
    @Override
    public List<DimensionResponseDTO> crear(DimensionBatchRequestDTO batchDTO) {

        if (batchDTO.dimensiones() == null || batchDTO.dimensiones().isEmpty()) {
            throw new DimensionValidationException("La lista de colores está vacía", "dimensiones");
        }

        log.info("Registrando {} colores", batchDTO.dimensiones().size());

        List<DimColor> colores = new ArrayList<>();

        for (DimensionRequestDTO dto : batchDTO.dimensiones()) {

            if (dto.nombre() == null || dto.nombre().isBlank() ||
                    dto.extra() == null || dto.extra().isBlank()) {
                throw new DimensionValidationException(
                        "El nombre y el código RGB del color son obligatorios",
                        "nombre|extra"
                );
            }

            if (colorRepo.existsByNombreAndCodeRgb(dto.nombre(), dto.extra())) {
                throw new DimensionValidationException(
                        "El color ya existe con ese código RGB",
                        "nombre|extra"
                );
            }

            DimColor color = new DimColor();
            color.setNombre(dto.nombre());
            color.setCodeRgb(dto.extra());

            colores.add(color);
        }

        List<DimColor> guardados = colorRepo.saveAll(colores);

        log.debug("Colores registrados exitosamente: {}", guardados.size());

        return guardados.stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * Actualiza un color existente.
     *
     * @param id  identificador único del color.
     * @param dto datos nuevos para actualizar el color.
     * @return color actualizado en formato {@link DimensionResponseDTO}.
     * @throws DimensionNotFoundException si el color no se encuentra.
     */
    @Override
    public DimensionResponseDTO actualizar(Integer id, DimensionRequestDTO dto) {

        log.debug("Actualizando color con ID {}", id);

        DimColor color = colorRepo.findById(id)
                .orElseThrow(() ->
                        new DimensionNotFoundException("El color con ID " + id + " no existe")
                );

        color.setNombre(dto.nombre());
        color.setCodeRgb(dto.extra());

        DimColor actualizado = colorRepo.save(color);

        log.info("Color {} actualizado correctamente", id);

        return mapper.toDTO(actualizado);
    }

    /**
     * Elimina un color por su identificador.
     *
     * @param id identificador del color a eliminar.
     * @throws DimensionNotFoundException si el color no existe.
     */
    @Override
    public void eliminar(Integer id) {

        log.warn("Intentando eliminar color con ID {}", id);

        if (!colorRepo.existsById(id)) {
            throw new DimensionNotFoundException("El color con ID " + id + " no existe");
        }

        colorRepo.deleteById(id);

        log.info("Color {} eliminado exitosamente", id);
    }

    /**
     * Busca un color por su ID.
     *
     * @param id identificador único del color.
     * @return color encontrado en formato {@link DimensionResponseDTO}.
     * @throws DimensionNotFoundException si no existe el color.
     */
    @Override
    public DimensionResponseDTO buscarPorId(Integer id) {

        log.debug("Buscando color con ID {}", id);

        DimColor color = colorRepo.findById(id)
                .orElseThrow(() ->
                        new DimensionNotFoundException("El color con ID " + id + " no existe")
                );

        return mapper.toDTO(color);
    }

    /**
     * Lista todos los colores en orden ascendente por ID.
     *
     * @return lista completa de colores registrados.
     */
    @Override
    public List<DimensionResponseDTO> listarTodo() {

        log.info("Listando todos los colores...");

        return colorRepo.findAllByOrderByIdAsc().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
