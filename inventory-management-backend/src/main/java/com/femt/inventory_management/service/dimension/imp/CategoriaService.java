package com.femt.inventory_management.service.dimension.imp;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.exceptions.DimensionNotFoundException;
import com.femt.inventory_management.exceptions.DimensionValidationException;
import com.femt.inventory_management.mapper.dimension.DimensionMapper;
import com.femt.inventory_management.models.dimension.DimCategoria;
import com.femt.inventory_management.repository.DimCategoriaRepository;
import com.femt.inventory_management.service.dimension.DimensionService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar la dimensión Categoría.
 * Implementación de los métodos CRUD para el manejo de categorías dentro del sistema.
 *
 * @author
 * @version 1.1
 * @since 2025-11-17
 */
@Slf4j
@Service
public class CategoriaService implements DimensionService {

    private final DimCategoriaRepository categoriaRepo;
    private final DimensionMapper mapper;

    public CategoriaService(DimCategoriaRepository categoriaRepo, DimensionMapper mapper) {
        this.categoriaRepo = categoriaRepo;
        this.mapper = mapper;
    }

    /**
     * Crea múltiples categorías a partir de un batch DTO.
     *
     * @param batchDTO contiene la lista de categorías a registrar.
     * @return lista de categorías en formato {@link DimensionResponseDTO}.
     * @throws DimensionValidationException si la lista viene vacía o los datos no son válidos.
     */
    @Override
    public List<DimensionResponseDTO> crear(DimensionBatchRequestDTO batchDTO) {

        if (batchDTO.dimensiones() == null || batchDTO.dimensiones().isEmpty()) {
            throw new DimensionValidationException(
                    "La lista de categorías está vacía",
                    "dimensiones"
            );
        }

        log.info("Registrando {} categorías", batchDTO.dimensiones().size());

        List<DimCategoria> categorias = new ArrayList<>();

        for (DimensionRequestDTO dto : batchDTO.dimensiones()) {

            if (dto.nombre() == null || dto.nombre().isBlank()) {
                throw new DimensionValidationException(
                        "El nombre de la categoría es obligatorio",
                        "nombre"
                );
            }

            if (categoriaRepo.existsByNombre(dto.nombre())) {
                throw new DimensionValidationException(
                        "La categoría ya existe",
                        "nombre"
                );
            }

            DimCategoria categoria = new DimCategoria();
            categoria.setNombre(dto.nombre());

            categorias.add(categoria);
        }

        List<DimCategoria> guardadas = categoriaRepo.saveAll(categorias);

        log.debug("Categorías registradas exitosamente: {}", guardadas.size());

        return guardadas.stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * Actualiza una categoría existente.
     *
     * @param id  identificador único de la categoría.
     * @param dto datos nuevos para actualizar la categoría.
     * @return categoría actualizada en formato {@link DimensionResponseDTO}.
     * @throws DimensionNotFoundException si no se encuentra la categoría.
     */
    @Override
    public DimensionResponseDTO actualizar(Integer id, DimensionRequestDTO dto) {

        log.debug("Actualizando categoría con ID {}", id);

        DimCategoria categoria = categoriaRepo.findById(id)
                .orElseThrow(() ->
                        new DimensionNotFoundException("La categoría con ID " + id + " no existe")
                );

        categoria.setNombre(dto.nombre());
        DimCategoria actualizada = categoriaRepo.save(categoria);

        log.info("Categoría {} actualizada correctamente", id);

        return mapper.toDTO(actualizada);
    }

    /**
     * Elimina una categoría por su identificador.
     *
     * @param id identificador único de la categoría a eliminar.
     * @throws DimensionNotFoundException si la categoría no existe.
     */
    @Override
    public void eliminar(Integer id) {

        log.warn("Intentando eliminar categoría con ID {}", id);

        if (!categoriaRepo.existsById(id)) {
            throw new DimensionNotFoundException("La categoría con ID " + id + " no existe");
        }

        categoriaRepo.deleteById(id);

        log.info("Categoría {} eliminada exitosamente", id);
    }

    /**
     * Busca una categoría por su ID.
     *
     * @param id identificador único de la categoría.
     * @return categoría encontrada en formato {@link DimensionResponseDTO}.
     * @throws DimensionNotFoundException si no existe la categoría.
     */
    @Override
    public DimensionResponseDTO buscarPorId(Integer id) {

        log.debug("Buscando categoría con ID {}", id);

        DimCategoria categoria = categoriaRepo.findById(id)
                .orElseThrow(() ->
                        new DimensionNotFoundException("La categoría con ID " + id + " no existe")
                );

        return mapper.toDTO(categoria);
    }

    /**
     * Lista todas las categorías en orden ascendente por ID.
     *
     * @return lista completa de categorías registradas.
     */
    @Override
    public List<DimensionResponseDTO> listarTodo() {

        log.info("Listando todas las categorías...");

        return categoriaRepo.findAllByOrderByIdAsc().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
