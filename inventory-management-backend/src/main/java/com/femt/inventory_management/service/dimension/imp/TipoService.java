package com.femt.inventory_management.service.dimension.imp;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.exceptions.DimensionNotFoundException;
import com.femt.inventory_management.exceptions.DimensionValidationException;
import com.femt.inventory_management.mapper.dimension.DimensionMapper;
import com.femt.inventory_management.models.dimension.DimTipoComponente;
import com.femt.inventory_management.repository.DimTipoComponenteRepository;
import com.femt.inventory_management.service.dimension.DimensionService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar la dimensión Tipo de Componente.
 * Implementación de los métodos CRUD y validaciones correspondientes.
 *
 * Este servicio administra la creación, modificación, consulta y eliminación
 * de tipos de componentes utilizados dentro del sistema.
 *
 * @author
 * @version 1.1
 * @since 2025-11-17
 */
@Slf4j
@Service
public class TipoService implements DimensionService {

    private final DimTipoComponenteRepository tipoRepo;
    private final DimensionMapper mapper;

    public TipoService(DimTipoComponenteRepository tipoRepo, DimensionMapper mapper) {
        this.tipoRepo = tipoRepo;
        this.mapper = mapper;
    }

    /**
     * Crea múltiples tipos de componentes a partir de un batch DTO.
     *
     * @param batchDTO contiene la lista de tipos de componentes a registrar.
     * @return lista de tipos en formato {@link DimensionResponseDTO}.
     * @throws DimensionValidationException si los datos son inválidos o la lista está vacía.
     */
    @Override
    public List<DimensionResponseDTO> crear(DimensionBatchRequestDTO batchDTO) {

        if (batchDTO.dimensiones() == null || batchDTO.dimensiones().isEmpty()) {
            throw new DimensionValidationException(
                    "La lista de tipos de componente está vacía",
                    "dimensiones"
            );
        }

        log.info("Registrando {} tipos de componentes", batchDTO.dimensiones().size());

        List<DimTipoComponente> tipos = new ArrayList<>();

        for (DimensionRequestDTO dto : batchDTO.dimensiones()) {

            if (dto.nombre() == null || dto.nombre().isBlank()) {
                throw new DimensionValidationException(
                        "El nombre del tipo de componente es obligatorio",
                        "nombre"
                );
            }

            if (tipoRepo.existsByNombre(dto.nombre())) {
                throw new DimensionValidationException(
                        "El tipo de componente ya existe",
                        "nombre"
                );
            }

            DimTipoComponente tipo = new DimTipoComponente();
            tipo.setNombre(dto.nombre());
            tipos.add(tipo);
        }

        List<DimTipoComponente> guardados = tipoRepo.saveAll(tipos);

        log.debug("Tipos de componentes registrados exitosamente: {}", guardados.size());

        return guardados.stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * Actualiza un tipo de componente existente.
     *
     * @param id  identificador único del tipo de componente.
     * @param dto datos nuevos a actualizar.
     * @return tipo actualizado en formato {@link DimensionResponseDTO}.
     * @throws DimensionNotFoundException si el tipo no existe.
     */
    @Override
    public DimensionResponseDTO actualizar(Integer id, DimensionRequestDTO dto) {

        log.debug("Actualizando tipo de componente con ID {}", id);

        DimTipoComponente tipo = tipoRepo.findById(id)
                .orElseThrow(() ->
                        new DimensionNotFoundException("El tipo de componente con ID " + id + " no existe")
                );

        tipo.setNombre(dto.nombre());

        DimTipoComponente actualizado = tipoRepo.save(tipo);

        log.info("Tipo de componente {} actualizado correctamente", id);

        return mapper.toDTO(actualizado);
    }

    /**
     * Elimina un tipo de componente por su identificador.
     *
     * @param id identificador único del tipo.
     * @throws DimensionNotFoundException si el tipo no existe.
     */
    @Override
    public void eliminar(Integer id) {

        log.warn("Intentando eliminar tipo de componente con ID {}", id);

        if (!tipoRepo.existsById(id)) {
            throw new DimensionNotFoundException(
                    "El tipo de componente con ID " + id + " no existe"
            );
        }

        tipoRepo.deleteById(id);

        log.info("Tipo de componente {} eliminado exitosamente", id);
    }

    /**
     * Busca un tipo de componente por su ID.
     *
     * @param id identificador único.
     * @return tipo encontrado en formato {@link DimensionResponseDTO}.
     * @throws DimensionNotFoundException si no se encuentra el tipo.
     */
    @Override
    public DimensionResponseDTO buscarPorId(Integer id) {

        log.debug("Buscando tipo de componente con ID {}", id);

        DimTipoComponente tipo = tipoRepo.findById(id)
                .orElseThrow(() ->
                        new DimensionNotFoundException("El tipo de componente con ID " + id + " no existe")
                );

        return mapper.toDTO(tipo);
    }

    /**
     * Lista todos los tipos de componentes registrados.
     *
     * @return lista completa de tipos de componentes.
     */
    @Override
    public List<DimensionResponseDTO> listarTodo() {

        log.info("Listando todos los tipos de componentes...");

        return tipoRepo.findAllByOrderByIdAsc().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
