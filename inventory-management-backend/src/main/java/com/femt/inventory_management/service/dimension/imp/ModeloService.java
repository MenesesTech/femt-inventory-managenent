package com.femt.inventory_management.service.dimension.imp;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.mapper.dimension.DimensionMapper;
import com.femt.inventory_management.models.dimension.DimModelo;
import com.femt.inventory_management.repository.DimModeloRepository;
import com.femt.inventory_management.service.dimension.DimensionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModeloService implements DimensionService {

    private final DimModeloRepository modeloRepo;
    private final DimensionMapper mapper;

    public ModeloService(DimModeloRepository modeloRepo, DimensionMapper mapper) {
        this.modeloRepo = modeloRepo;
        this.mapper = mapper;
    }

    /**
     * Crear multiples modelos
     * @param batchDTO contiene la lista de modelos a registrar
     * @return lista de modelos en él response {@link DimensionResponseDTO}
     */
    @Override
    public List<DimensionResponseDTO> crear(DimensionBatchRequestDTO batchDTO) {
        if (batchDTO.dimensiones().isEmpty()){
            throw new IllegalArgumentException("Servicio Modelo -> Campos del modelo vacíos");
        }
        List<DimModelo> modelos = new ArrayList<>();
        for (DimensionRequestDTO dto : batchDTO.dimensiones()){
            if (dto.nombre().isEmpty() || dto.nombre() == null){
                throw new IllegalArgumentException("Servicio Modelo -> La lista de modelos no pueden estar vacíos");
            }

            if (modeloRepo.existsByNombre(dto.nombre())){
                throw new IllegalArgumentException("Servicio Modelo -> Ese modelo ya existe");
            }
            DimModelo modelo = new DimModelo();
            modelo.setNombre(dto.nombre());
            modelos.add(modelo);
        }
        List<DimModelo> dimModelosGuardados = modeloRepo.saveAll(modelos);
        return dimModelosGuardados.stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * Actualiza un modelo ya existente
     * @param id identificador único del modelo a modificar
     * @param dto datos nuevos a actualizar
     * @return el modelo actualizado convertido a {@link DimensionResponseDTO}
     */
    @Override
    public DimensionResponseDTO actualizar(Integer id, DimensionRequestDTO dto) {
        DimModelo modelo = modeloRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio Modelo -> Modelo no encontrado"));
        modelo.setNombre(dto.nombre());
        DimModelo actualizado = modeloRepo.save(modelo);
        return mapper.toDTO(actualizado);
    }

    /**
     * Elimina un modelo por ID
     * @param id identificador del modelo a eliminar.
     * @throws EntityNotFoundException si el modelo no existe
     */
    @Override
    public void eliminar(Integer id) {
        if (!modeloRepo.existsById(id)){
            throw new EntityNotFoundException("Servicio Modelo -> Modelo no encontrado con el ID: " + id);
        }
        modeloRepo.deleteById(id);
    }

    /**
     * Busca un modelo por su ID
     * @param id identificador del modelo
     * @return modelo encontrado en formato {@link DimensionResponseDTO}
     */
    @Override
    public DimensionResponseDTO buscarPorId(Integer id) {
        var modelo = modeloRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio Modelo -> Modelo no existe"));
        return mapper.toDTO(modelo);
    }

    /**
     * Lista todas los modelo ordenados por ID ascendente
     * @return lista completa de modelos registrados
     */
    @Override
    public List<DimensionResponseDTO> listarTodo() {
        return modeloRepo.findAllByOrderByIdAsc().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
