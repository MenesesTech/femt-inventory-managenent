package com.femt.inventory_management.service.serie.imp;

import com.femt.inventory_management.dto.request.KitSerieColorBatchRequest;
import com.femt.inventory_management.dto.request.KitSerieColorRequestDTO;
import com.femt.inventory_management.dto.response.KitSerieColorResponseDTO;
import com.femt.inventory_management.exceptions.KitSerieNotFoundException;
import com.femt.inventory_management.exceptions.KitSerieValidationException;
import com.femt.inventory_management.mapper.kit.KitSerieColorMapper;
import com.femt.inventory_management.models.dimension.*;
import com.femt.inventory_management.models.kit.KitSerieColor;
import com.femt.inventory_management.repository.*;
import com.femt.inventory_management.service.serie.SerieColoresService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar la creación, consulta, actualización y eliminación
 * de combinaciones de colores para el módulo Kit Serie.
 *
 * Una combinación representa el color asignado a una posición dentro de una matriz definida por:
 * fila, columna, modelo, categoría y tipo de componente (Tira o Planta).
 *
 * Este servicio permite:
 * - Registrar múltiples combinaciones en lote.
 * - Consultar combinaciones por ID.
 * - Obtener la matriz completa según modelo, categoría y tipo.
 * - Actualizar el color de una combinación específica.
 * - Eliminar todas las combinaciones asociadas a un modelo, categoría y tipo de componente.
 */
@Slf4j
@Service
public class SerieColoresServiceImp implements SerieColoresService {

    private final KitSerieColorRepository seriecolorRepo;
    private final DimFilaRepository filaRepo;
    private final DimColumnaRepository columnaRepo;
    private final DimModeloRepository modeloRepo;
    private final DimColorRepository colorRepo;
    private final DimCategoriaRepository categoriaRepo;
    private final DimTipoComponenteRepository tipoComponenteRepo;
    private final KitSerieColorMapper mapper;

    public SerieColoresServiceImp(KitSerieColorRepository seriecolorRepo,
                                  DimFilaRepository filaRepo,
                                  DimColumnaRepository columnaRepo,
                                  DimModeloRepository modeloRepo,
                                  DimColorRepository colorRepo,
                                  DimCategoriaRepository categoriaRepo,
                                  DimTipoComponenteRepository tipoComponenteRepo,
                                  KitSerieColorMapper mapper) {
        this.seriecolorRepo = seriecolorRepo;
        this.filaRepo = filaRepo;
        this.columnaRepo = columnaRepo;
        this.modeloRepo = modeloRepo;
        this.colorRepo = colorRepo;
        this.categoriaRepo = categoriaRepo;
        this.tipoComponenteRepo = tipoComponenteRepo;
        this.mapper = mapper;
    }

    /**
     * Registra un conjunto de combinaciones de colores en lote.
     * Cada combinación corresponde a una posición fila-columna para un modelo,
     * categoría y tipo de componente específico.
     *
     * El método valida:
     * - Que la lista no esté vacía.
     * - Que cada registro tenga los campos obligatorios.
     * - Que los IDs de dimensiones existan en la base de datos.
     *
     * @param request lista de combinaciones a registrar
     * @return lista de combinaciones guardadas en formato DTO
     * @throws KitSerieValidationException si faltan campos obligatorios
     * @throws KitSerieNotFoundException si alguna dimensión no existe
     */
    @Override
    public List<KitSerieColorResponseDTO> guardarSerieColores(KitSerieColorBatchRequest request) {

        if (request.colores() == null || request.colores().isEmpty()) {
            throw new KitSerieValidationException("La lista de series está vacía", "colores");
        }

        log.info("Registrando {} colores", request.colores().size());

        List<KitSerieColor> serieColores = new ArrayList<>();

        for (KitSerieColorRequestDTO dto : request.colores()) {

            validarCamposObligatorios(dto);

            DimFila fila = filaRepo.findById(dto.idFila())
                    .orElseThrow(() -> new KitSerieNotFoundException("La fila no existe"));

            DimColumna columna = columnaRepo.findById(dto.idColumna())
                    .orElseThrow(() -> new KitSerieNotFoundException("La columna no existe"));

            DimModelo modelo = modeloRepo.findById(dto.idModelo())
                    .orElseThrow(() -> new KitSerieNotFoundException("El modelo no existe"));

            DimColor color = colorRepo.findById(dto.idColor())
                    .orElseThrow(() -> new KitSerieNotFoundException("El color no existe"));

            DimCategoria categoria = categoriaRepo.findById(dto.idCategoria())
                    .orElseThrow(() -> new KitSerieNotFoundException("La categoría no existe"));

            DimTipoComponente tipo = tipoComponenteRepo.findById(dto.idTipoComponente())
                    .orElseThrow(() -> new KitSerieNotFoundException("El tipo de componente no existe"));

            KitSerieColor entidad = new KitSerieColor();
            entidad.setFila(fila);
            entidad.setColumna(columna);
            entidad.setModelo(modelo);
            entidad.setColor(color);
            entidad.setCategoria(categoria);
            entidad.setTipoComponente(tipo);

            serieColores.add(entidad);
        }

        List<KitSerieColor> guardadas = seriecolorRepo.saveAll(serieColores);

        log.debug("Series de colores registradas: {}", guardadas.size());

        return guardadas.stream().map(mapper::toDTO).toList();
    }

    /**
     * Obtiene una combinación por su ID.
     *
     * @param id identificador de la combinación
     * @return DTO con los datos de la combinación
     * @throws KitSerieNotFoundException si no existe
     */
    @Override
    @Transactional(readOnly = true)
    public KitSerieColorResponseDTO obtenerPorId(Integer id) {

        log.info("Buscando serie de color con id {}", id);

        KitSerieColor serieColor = seriecolorRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Serie de color no encontrada con id {}", id);
                    return new KitSerieNotFoundException("No se encontró la serie de color con id " + id);
                });

        log.debug("Serie de color encontrada: {}", serieColor.getId());

        return mapper.toDTO(serieColor);
    }

    /**
     * Obtiene todas las combinaciones registradas para un modelo,
     * categoría y tipo de componente.
     *
     * Se utiliza para recuperar la matriz completa de colores.
     *
     * @param idModelo id del modelo
     * @param idCategoria id de la categoría
     * @param idTipoComponente id del tipo de componente
     * @return lista de combinaciones encontradas en formato DTO
     * @throws KitSerieNotFoundException si no existen combinaciones
     */
    @Override
    @Transactional(readOnly = true)
    public List<KitSerieColorResponseDTO> obtenerPorModeloCategoriaTipo(Integer idModelo,
                                                                        Integer idCategoria,
                                                                        Integer idTipoComponente) {

        log.info("Consultando series por modelo={}, categoria={}, tipo={}",
                idModelo, idCategoria, idTipoComponente);

        DimModelo modelo = modeloRepo.findById(idModelo)
                .orElseThrow(() -> {
                    log.error("Modelo no encontrado con ID {}", idModelo);
                    return new KitSerieNotFoundException("Modelo no encontrado");
                });

        DimCategoria categoria = categoriaRepo.findById(idCategoria)
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada con ID {}", idCategoria);
                    return new KitSerieNotFoundException("Categoría no encontrada");
                });

        DimTipoComponente tipo = tipoComponenteRepo.findById(idTipoComponente)
                .orElseThrow(() -> {
                    log.error("Tipo de componente no encontrado con ID {}", idTipoComponente);
                    return new KitSerieNotFoundException("Tipo no encontrado");
                });

        List<KitSerieColor> lista =
                seriecolorRepo.findByModeloAndCategoriaAndTipoComponente(modelo, categoria, tipo);

        if (lista.isEmpty()) {
            log.warn("No se encontraron combinaciones para los parámetros dados");
            throw new KitSerieNotFoundException(
                    "No existen combinaciones registradas para esos parámetros");
        }

        log.info("Se encontraron {} combinaciones", lista.size());

        return lista.stream().map(mapper::toDTO).toList();
    }

    /**
     * Actualiza únicamente el color asignado a una combinación específica.
     *
     * @param id identificador de la combinación a actualizar
     * @param nuevoColorId id del nuevo color
     * @return DTO con la combinación actualizada
     * @throws KitSerieNotFoundException si la combinación o el color no existen
     */
    @Override
    @Transactional(readOnly = true)
    public KitSerieColorResponseDTO actualizarColor(Integer id, Integer nuevoColorId) {

        log.info("Solicitando actualización del color en registro id={} a nuevoColorId={}",
                id, nuevoColorId);

        KitSerieColor serieColor = seriecolorRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("No existe combinación con ID {}", id);
                    return new KitSerieNotFoundException("No se encontró la serie de color");
                });

        DimColor nuevoColor = colorRepo.findById(nuevoColorId)
                .orElseThrow(() -> {
                    log.error("No existe el color con ID {}", nuevoColorId);
                    return new KitSerieNotFoundException("El color no existe");
                });

        serieColor.setColor(nuevoColor);

        KitSerieColor actualizado = seriecolorRepo.save(serieColor);

        log.info("Color actualizado correctamente para el registro id={}", id);

        return mapper.toDTO(actualizado);
    }

    /**
     * Elimina todas las combinaciones de un modelo, categoría y tipo de componente.
     * Se utiliza cuando se necesita regenerar la matriz completa.
     *
     * @param idModelo id del modelo
     * @param idCategoria id de la categoría
     * @param idTipoComponente id del tipo de componente
     * @throws KitSerieNotFoundException si alguna dimensión no existe
     */
    @Override
    @Transactional(readOnly = false)
    public void eliminarPorModeloCategoriaTipo(Integer idModelo,
                                               Integer idCategoria,
                                               Integer idTipoComponente) {

        DimModelo modelo = modeloRepo.findById(idModelo)
                .orElseThrow(() -> new KitSerieNotFoundException("El modelo no existe"));

        DimCategoria categoria = categoriaRepo.findById(idCategoria)
                .orElseThrow(() -> new KitSerieNotFoundException("La categoría no existe"));

        DimTipoComponente tipo = tipoComponenteRepo.findById(idTipoComponente)
                .orElseThrow(() -> new KitSerieNotFoundException("El tipo de componente no existe"));

        seriecolorRepo.deleteByModeloAndCategoriaAndTipoComponente(modelo, categoria, tipo);

        log.info("Eliminación completada para modelo={}, categoría={}, tipo={}",
                idModelo, idCategoria, idTipoComponente);
    }

    /**
     * Verifica que los campos mínimos obligatorios estén presentes en la solicitud.
     * Esta validación se ejecuta antes de consultar las dimensiones en la base de datos.
     *
     * @param dto registro del request
     * @throws KitSerieValidationException si falta algún campo
     */
    private void validarCamposObligatorios(KitSerieColorRequestDTO dto) {

        if (dto.idFila() == null) {
            throw new KitSerieValidationException("El id de la fila es obligatorio", "idFila");
        }

        if (dto.idColumna() == null) {
            throw new KitSerieValidationException("El id de la columna es obligatorio", "idColumna");
        }

        if (dto.idModelo() == null) {
            throw new KitSerieValidationException("El ID del modelo es obligatorio", "idModelo");
        }

        if (dto.idColor() == null) {
            throw new KitSerieValidationException("El ID del color es obligatorio", "idColor");
        }

        if (dto.idCategoria() == null) {
            throw new KitSerieValidationException("El ID de la categoría es obligatorio", "idCategoria");
        }

        if (dto.idTipoComponente() == null) {
            throw new KitSerieValidationException("El ID del tipo de componente es obligatorio", "idTipoComponente");
        }
    }
}

