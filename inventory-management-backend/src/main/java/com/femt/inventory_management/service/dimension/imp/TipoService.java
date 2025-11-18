package com.femt.inventory_management.service.dimension.imp;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.mapper.dimension.DimensionMapper;
import com.femt.inventory_management.models.dimension.DimTipoComponente;
import com.femt.inventory_management.repository.DimTipoComponenteRepository;
import com.femt.inventory_management.service.dimension.DimensionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TipoService implements DimensionService {
    private final DimTipoComponenteRepository tipoRepo;
    private final DimensionMapper mapper;

    public TipoService(DimTipoComponenteRepository tipoRepo, DimensionMapper mapper) {
        this.tipoRepo = tipoRepo;
        this.mapper = mapper;
    }

    /**
     * Crear multiples tipos de componentes
     * @param batchDTO contiene la lista de tipos de componentes a registrar
     * @return lista de tipos de componentes en él response {@link DimensionResponseDTO}
     */
    @Override
    public List<DimensionResponseDTO> crear(DimensionBatchRequestDTO batchDTO) {
        if (batchDTO.dimensiones().isEmpty()){
            throw new IllegalArgumentException("Servicio Tipos -> Campos de la talla vacíos");
        }
        List<DimTipoComponente> tipos = new ArrayList<>();
        for (DimensionRequestDTO dto : batchDTO.dimensiones()){
            if (dto.nombre().isEmpty() || dto.nombre() == null){
                throw new IllegalArgumentException("Servicio Tipos -> La lista de tipos de componentes no pueden estar vacíos");
            }

            if (tipoRepo.existsByNombre(dto.nombre())){
                throw new IllegalArgumentException("Servicio Tipos -> Ese tipo de componente ya existe");
            }
            DimTipoComponente tipo = new DimTipoComponente();
            tipo.setNombre(dto.nombre());
            tipos.add(tipo);
        }
        List<DimTipoComponente> dimTiposGuardados = tipoRepo.saveAll(tipos);
        return dimTiposGuardados.stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * Actualiza un tipo ya existente
     * @param id identificador único del tipo a modificar
     * @param dto datos nuevos a actualizar
     * @return tipo actualizada convertido a {@link DimensionResponseDTO}
     */
    @Override
    public DimensionResponseDTO actualizar(Integer id, DimensionRequestDTO dto) {
        DimTipoComponente tipo = tipoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio Tipos -> Tipo de componente no encontrado"));
        tipo.setNombre(dto.nombre());
        DimTipoComponente actualizado = tipoRepo.save(tipo);
        return mapper.toDTO(actualizado);
    }

    /**
     * Elimina un tipo de componente por ID
     * @param id identificador del tipo a eliminar.
     * @throws EntityNotFoundException si el tipo no existe
     */
    @Override
    public void eliminar(Integer id) {
        if (!tipoRepo.existsById(id)){
            throw new EntityNotFoundException("Servicio Tipos -> Tipo no encontrado con el ID: " + id);
        }
        tipoRepo.deleteById(id);
    }

    /**
     * Busca un modelo por su ID
     * @param id identificador del modelo
     * @return modelo encontrado en formato {@link DimensionResponseDTO}
     */
    @Override
    public DimensionResponseDTO buscarPorId(Integer id) {
        var tipo = tipoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio Talla -> Talla no existe"));
        return mapper.toDTO(tipo);
    }

    /**
     * Lista todos los tipos ordenados por ID ascendente
     * @return lista completa de tipos registrados
     */
    @Override
    public List<DimensionResponseDTO> listarTodo() {
        return tipoRepo.findAllByOrderByIdAsc().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
