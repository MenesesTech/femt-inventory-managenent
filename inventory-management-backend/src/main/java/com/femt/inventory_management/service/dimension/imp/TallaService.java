package com.femt.inventory_management.service.dimension.imp;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.mapper.dimension.DimensionMapper;
import com.femt.inventory_management.models.dimension.DimTalla;
import com.femt.inventory_management.repository.DimTallaRepository;
import com.femt.inventory_management.service.dimension.DimensionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TallaService implements DimensionService {
    private final DimTallaRepository tallaRepo;
    private final DimensionMapper mapper;

    public TallaService(DimTallaRepository tallaRepo, DimensionMapper mapper) {
        this.tallaRepo = tallaRepo;
        this.mapper = mapper;
    }

    /**
     * Crear multiples tallas
     * @param batchDTO contiene la lista de tallas a registrar
     * @return lista de modelos en él response {@link DimensionResponseDTO}
     */
    @Override
    public List<DimensionResponseDTO> crear(DimensionBatchRequestDTO batchDTO) {
        if (batchDTO.dimensiones().isEmpty()){
            throw new IllegalArgumentException("Servicio Talla -> Campos de la talla vacíos");
        }
        List<DimTalla> tallas = new ArrayList<>();
        for (DimensionRequestDTO dto : batchDTO.dimensiones()){
            if (dto.nombre().isEmpty() || dto.nombre() == null){
                throw new IllegalArgumentException("Servicio Talla -> La lista de tallas no pueden estar vacíos");
            }

            if (tallaRepo.existsByNombre(dto.nombre())){
                throw new IllegalArgumentException("Servicio Talla -> Esa talla ya existe");
            }
            DimTalla talla = new DimTalla();
            talla.setNombre(dto.nombre());
            tallas.add(talla);
        }
        List<DimTalla> dimTallasGuardadas = tallaRepo.saveAll(tallas);
        return dimTallasGuardadas.stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * Actualiza una talla ya existente
     * @param id identificador único de la talla a modificar
     * @param dto datos nuevos a actualizar
     * @return talla actualizada convertido a {@link DimensionResponseDTO}
     */
    @Override
    public DimensionResponseDTO actualizar(Integer id, DimensionRequestDTO dto) {
        DimTalla talla = tallaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio Talla -> Talla no encontrado"));
        talla.setNombre(dto.nombre());
        DimTalla actualizado = tallaRepo.save(talla);
        return mapper.toDTO(actualizado);
    }

    /**
     * Elimina un modelo por ID
     * @param id identificador del modelo a eliminar.
     * @throws EntityNotFoundException si el modelo no existe
     */
    @Override
    public void eliminar(Integer id) {
        if (!tallaRepo.existsById(id)){
            throw new EntityNotFoundException("Servicio Talla -> Talla no encontrado con el ID: " + id);
        }
        tallaRepo.deleteById(id);
    }

    /**
     * Busca un tipo de componente por su ID
     * @param id identificador del modelo
     * @return tipo encontrado en formato {@link DimensionResponseDTO}
     */
    @Override
    public DimensionResponseDTO buscarPorId(Integer id) {
        var talla = tallaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio Talla -> Talla no existe"));
        return mapper.toDTO(talla);
    }

    /**
     * Lista todas las tallas ordenados por ID ascendente
     * @return lista completa de modelos registrados
     */
    @Override
    public List<DimensionResponseDTO> listarTodo() {
        return tallaRepo.findAllByOrderByIdAsc().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
