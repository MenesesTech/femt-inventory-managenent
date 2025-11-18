package com.femt.inventory_management.service.dimension.imp;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.mapper.dimension.DimensionMapper;
import com.femt.inventory_management.models.dimension.DimColor;
import com.femt.inventory_management.repository.DimColorRepository;
import com.femt.inventory_management.service.dimension.DimensionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColorService implements DimensionService {

    private final DimColorRepository colorRepo;
    private final DimensionMapper mapper;

    public ColorService(DimColorRepository colorRepo, DimensionMapper mapper) {
        this.colorRepo = colorRepo;
        this.mapper = mapper;
    }

    @Override
    public List<DimensionResponseDTO> crear(DimensionBatchRequestDTO batchDTO) {

        if (batchDTO.dimensiones().isEmpty()) {
            throw new IllegalArgumentException(
                    "Servicio Colores -> Campos vacíos, no se puede registrar los colores"
            );
        }

        List<DimColor> colores = new ArrayList<>();

        for (DimensionRequestDTO dto : batchDTO.dimensiones()) {

            if (dto.nombre() == null || dto.nombre().isEmpty() ||
                    dto.extra() == null || dto.extra().isEmpty()) {
                throw new IllegalArgumentException(
                        "Servicio Colores -> Los colores no pueden estar vacíos"
                );
            }

            if (colorRepo.existsByNombreAndCodeRgb(dto.nombre(), dto.extra())) {
                throw new IllegalArgumentException(
                        "Servicio Colores -> Ese color ya existe con ese código RGB"
                );
            }

            DimColor color = new DimColor();
            color.setNombre(dto.nombre());
            color.setCodeRgb(dto.extra());

            colores.add(color);
        }

        List<DimColor> guardados = colorRepo.saveAll(colores);

        return guardados.stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public DimensionResponseDTO actualizar(Integer id, DimensionRequestDTO dto) {

        DimColor color = colorRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Servicio Colores -> Color no encontrado")
                );

        color.setNombre(dto.nombre());
        color.setCodeRgb(dto.extra());

        DimColor actualizado = colorRepo.save(color);

        return mapper.toDTO(actualizado);
    }

    @Override
    public void eliminar(Integer id) {

        if (!colorRepo.existsById(id)) {
            throw new EntityNotFoundException(
                    "Servicio Colores -> Color no encontrado con el id " + id
            );
        }
        colorRepo.deleteById(id);
    }

    @Override
    public DimensionResponseDTO buscarPorId(Integer id) {

        DimColor color = colorRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Servicio Colores -> Color no existe")
                );
        return mapper.toDTO(color);
    }

    @Override
    public List<DimensionResponseDTO> listarTodo() {
        return colorRepo.findAllByOrderByIdAsc().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
