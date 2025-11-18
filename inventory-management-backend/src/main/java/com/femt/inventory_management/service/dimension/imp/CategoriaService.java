package com.femt.inventory_management.service.dimension.imp;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.mapper.dimension.DimensionMapper;
import com.femt.inventory_management.models.dimension.DimCategoria;
import com.femt.inventory_management.repository.DimCategoriaRepository;
import com.femt.inventory_management.service.dimension.DimensionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio Categoria
 * Implementacion de metodos CRUD para el manejo de categorias
 *
 * @author MenesesTech
 * @version 1.0
 * @since 2025-11-17
 */
@Service
public class CategoriaService implements DimensionService {
    public final DimCategoriaRepository categoriaRepo;
    public DimensionMapper mapper;

    public CategoriaService(DimCategoriaRepository categoriaRepo, DimensionMapper mapper) {
        this.categoriaRepo = categoriaRepo;
        this.mapper = mapper;
    }

    /**
     * Crea multiples categorias
     * @param batchDTO contiene lista de categorias a registrar
     * @return lista de categorias en él response {@link DimensionResponseDTO}
     */
    @Override
    public List<DimensionResponseDTO> crear(DimensionBatchRequestDTO batchDTO) {
       if (batchDTO.dimensiones().isEmpty()){
           throw new IllegalArgumentException("Servicio Categoría -> Campos vacíos, no se puede registrar la categoria");
       }

       List<DimCategoria> categorias = new ArrayList<>();

       for (DimensionRequestDTO dto : batchDTO.dimensiones()){
           if (dto.nombre() == null || dto.nombre().isEmpty()){
               throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
           }
           if (categoriaRepo.existsByNombre(dto.nombre())) {
               throw new IllegalArgumentException("La categoría ya existe");
           }


           DimCategoria categoria = new DimCategoria();
           categoria.setNombre(dto.nombre());
           categorias.add(categoria);
        }

       List<DimCategoria> dimCategoriasGuardadas = categoriaRepo.saveAll(categorias);
       return dimCategoriasGuardadas.stream()
               .map(mapper::toDTO)
               .toList();
    }

    /**
     * Actualiza una categoria ya existente
     * @param id identificador único de la categoria a modificar
     * @param dto datos nuevos a actualizar
     * @return la categoría actualizada convertida a {@link DimensionResponseDTO}
     */
    @Override
    public DimensionResponseDTO actualizar(Integer id, DimensionRequestDTO dto) {
        DimCategoria categoria = categoriaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio categoría -> Categoría no encontrada"));
        categoria.setNombre(dto.nombre());
        return mapper.toDTO(categoriaRepo.save(categoria));
    }

    /**
     * Elimina una categoría por ID
     * @param id identificador de la categoría a eliminar.
     * @throws EntityNotFoundException si la categoría no existe
     */
    @Override
    public void eliminar(Integer id) {
        if (!categoriaRepo.existsById(id))
            throw new EntityNotFoundException("Servicio categoría -> Categoría no existe");
        categoriaRepo.deleteById(id);
    }

    /**
     * Busca una categoría por su ID
     * @param id identificador de la categoría
     * @return categoría encontrada en formato {@link DimensionResponseDTO}
     */
    @Override
    public DimensionResponseDTO buscarPorId(Integer id) {
        DimCategoria categoria = categoriaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio categoría -> Categoría no existe"));
        return mapper.toDTO(categoria);
    }

    /**
     * Lista todas las categorías ordenadas por ID ascendente
     * @return lista completa de categorías registradas
     */
    @Override
    public List<DimensionResponseDTO> listarTodo() {
        return categoriaRepo.findAllByOrderByIdAsc().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
