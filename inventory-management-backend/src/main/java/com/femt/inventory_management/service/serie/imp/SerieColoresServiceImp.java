package com.femt.inventory_management.service.serie.imp;

import com.femt.inventory_management.dto.request.KitSerieBatchRequestDTO;
import com.femt.inventory_management.dto.request.KitSerieItemRequestDTO;
import com.femt.inventory_management.dto.response.KitSerieResponseDTO;
import com.femt.inventory_management.mapper.kit.KitSerieMapper;
import com.femt.inventory_management.models.dimension.*;
import com.femt.inventory_management.models.kit.KitSerie;
import com.femt.inventory_management.models.kit.KitSerieCode;
import com.femt.inventory_management.repository.*;
import com.femt.inventory_management.service.serie.SerieColoresService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio encargado de gestionar la creación, consulta, actualización y eliminación
 * de combinaciones de series de colores para Tira y Planta dentro del módulo de Kit Serie.
 *
 * Implementa la lógica de negocio necesaria para:
 * - Registrar nuevas combinaciones de colores por talla, modelo y categoría.
 * - Evitar duplicados de combinaciones previamente registradas.
 * - Consultar series existentes bajo diferentes criterios.
 * - Actualizar colores asociados a una serie.
 * - Eliminar registros.
 *
 * Este servicio opera sobre dos tipos de componentes: Tira y Planta.
 */
@Service
public class SerieColoresServiceImp implements SerieColoresService {

    // Repositorios de Dimensiones
    private final DimModeloRepository modeloRepo;
    private final DimTallaRepository tallaRepo;
    private final DimColorRepository colorRepo;
    private final DimCategoriaRepository categoriaRepo;
    private final DimTipoComponenteRepository tipoComponenteRepo;

    // Repositorios de Kit Serie
    private final KitSerieRepository serieRepo;
    private final KitSerieCodeRepository serieCodeRepo;

    // Mapper para convertir entidades en DTO
    private final KitSerieMapper kitSerieMapper;

    /**
     * Constructor con inyección de dependencias.
     */
    public SerieColoresServiceImp(DimModeloRepository modeloRepo,
                                  DimTallaRepository tallaRepo,
                                  DimColorRepository colorRepo,
                                  DimCategoriaRepository categoriaRepo,
                                  DimTipoComponenteRepository tipoComponenteRepo,
                                  KitSerieRepository serieRepo,
                                  KitSerieCodeRepository serieCodeRepo,
                                  KitSerieMapper kitSerieMapper) {
        this.modeloRepo = modeloRepo;
        this.tallaRepo = tallaRepo;
        this.colorRepo = colorRepo;
        this.categoriaRepo = categoriaRepo;
        this.tipoComponenteRepo = tipoComponenteRepo;
        this.serieRepo = serieRepo;
        this.serieCodeRepo = serieCodeRepo;
        this.kitSerieMapper = kitSerieMapper;
    }

    /**
     * Registra un conjunto de combinaciones de series de colores para Tira y Planta.
     *
     * Cada combinación contiene:
     *  - Talla
     *  - Color de Tira
     *  - Color de Planta
     *
     * El método realiza:
     *  1. Validación del request.
     *  2. Carga de entidades maestras (modelo, categoría, serieCode, tipos de componente).
     *  3. Recorrido de las combinaciones enviadas.
     *  4. Validación para evitar duplicados en BD.
     *  5. Registro de nuevas series en batch.
     *
     * Si una combinación ya existe para un tipo de componente, no se vuelve a registrar.
     *
     * @param requestDTO DTO con la información del modelo, categoría, serieCode y todas las combinaciones.
     * @return Lista de DTO con las combinaciones registradas exitosamente.
     * @throws IllegalArgumentException si el request está vacío.
     * @throws EntityNotFoundException si alguna dimensión no existe.
     */
    @Override
    @Transactional
    public List<KitSerieResponseDTO> guardarSerie(KitSerieBatchRequestDTO requestDTO) {

        if (requestDTO == null || requestDTO.combinaciones() == null || requestDTO.combinaciones().isEmpty()) {
            throw new IllegalArgumentException("Servicio de Series de Colores -> No hay combinaciones para registrar.");
        }

        DimModelo modelo = modeloRepo.findById(requestDTO.idModelo())
                .orElseThrow(() -> new EntityNotFoundException("Modelo no encontrado: " + requestDTO.idModelo()));

        DimCategoria categoria = categoriaRepo.findById(requestDTO.idCategoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada: " + requestDTO.idCategoria()));

        KitSerieCode serieCode = serieCodeRepo.findById(requestDTO.idSerieCode())
                .orElseThrow(() -> new EntityNotFoundException("Código de serie no encontrado: " + requestDTO.idSerieCode()));

        // Tipos de componentes requeridos
        DimTipoComponente tipoTira = tipoComponenteRepo.findByNombre("Tira")
                .orElseThrow(() -> new EntityNotFoundException("Tipo 'Tira' no encontrado en dim_tipo_componente"));

        DimTipoComponente tipoPlanta = tipoComponenteRepo.findByNombre("Planta")
                .orElseThrow(() -> new EntityNotFoundException("Tipo 'Planta' no encontrado en dim_tipo_componente"));

        List<KitSerie> seriesAGuardar = new ArrayList<>();

        for (KitSerieItemRequestDTO item : requestDTO.combinaciones()) {

            DimTalla talla = tallaRepo.findById(item.idTalla())
                    .orElseThrow(() -> new EntityNotFoundException("Talla no encontrada: " + item.idTalla()));

            DimColor colorTira = colorRepo.findById(item.idColorTira())
                    .orElseThrow(() -> new EntityNotFoundException("Color de Tira no encontrado: " + item.idColorTira()));

            DimColor colorPlanta = colorRepo.findById(item.idColorPlanta())
                    .orElseThrow(() -> new EntityNotFoundException("Color de Planta no encontrado: " + item.idColorPlanta()));

            // Registro TIRA
            Optional<KitSerie> existenteTira = serieRepo.buscarPorModeloTallaColorCategoriaTipoComponenteSerieCode(
                    serieCode.getId(), modelo.getId(), talla.getId(), colorTira.getId(),
                    categoria.getId(), tipoTira.getId());

            if (existenteTira.isEmpty()) {
                KitSerie nuevaTira = new KitSerie();
                nuevaTira.setSerieCode(serieCode);
                nuevaTira.setModelo(modelo);
                nuevaTira.setTalla(talla);
                nuevaTira.setColor(colorTira);
                nuevaTira.setCategoria(categoria);
                nuevaTira.setTipoComponente(tipoTira);
                seriesAGuardar.add(nuevaTira);
            }

            // Registro PLANTA
            Optional<KitSerie> existentePlanta = serieRepo.buscarPorModeloTallaColorCategoriaTipoComponenteSerieCode(
                    serieCode.getId(), modelo.getId(), talla.getId(), colorPlanta.getId(),
                    categoria.getId(), tipoPlanta.getId());

            if (existentePlanta.isEmpty()) {
                KitSerie nuevaPlanta = new KitSerie();
                nuevaPlanta.setSerieCode(serieCode);
                nuevaPlanta.setModelo(modelo);
                nuevaPlanta.setTalla(talla);
                nuevaPlanta.setColor(colorPlanta);
                nuevaPlanta.setCategoria(categoria);
                nuevaPlanta.setTipoComponente(tipoPlanta);
                seriesAGuardar.add(nuevaPlanta);
            }
        }

        List<KitSerie> seriesGuardadas = serieRepo.saveAll(seriesAGuardar);
        return kitSerieMapper.toDTOList(seriesGuardadas);
    }

    /**
     * Obtiene una lista completa con todas las series de colores registradas en el sistema.
     *
     * @return Lista de DTO representando todas las series existentes.
     */
    @Override
    public List<KitSerieResponseDTO> listarTodo() {
        return serieRepo.findAll()
                .stream()
                .map(kitSerieMapper::toDTO)
                .toList();
    }

    /**
     * Obtiene todas las series registradas asociadas a un modelo y categoría específicos.
     *
     * @param idModelo    Identificador del modelo.
     * @param idCategoria Identificador de la categoría.
     * @return Lista de DTO con las series filtradas por modelo y categoría.
     */
    @Override
    public List<KitSerieResponseDTO> listarPorModeloCategoria(Integer idModelo, Integer idCategoria) {
        List<KitSerie> lista = serieRepo.findByModeloAndCategoria(idModelo, idCategoria);
        return kitSerieMapper.toDTOList(lista);
    }

    /**
     * Devuelve una tabla organizada de series según:
     * - Modelo
     * - Categoría
     * - Código de Serie (A1, B1, C1, D1)
     *
     * La tabla viene ordenada por talla y tipo de componente (Tira/Planta),
     * ideal para ser consumida directamente por el Frontend (React).
     *
     * @param idModelo    Identificador del modelo.
     * @param idCategoria Identificador de la categoría.
     * @param idSerieCode Identificador del código de serie.
     * @return Lista organizada de series como DTO.
     */
    @Override
    public List<KitSerieResponseDTO> obtenerTablaSeries(Integer idModelo, Integer idCategoria, Integer idSerieCode) {
        List<KitSerie> lista = serieRepo.buscarTablaOrganizada(idModelo, idCategoria, idSerieCode);
        return lista.stream().map(kitSerieMapper::toDTO).toList();
    }

    /**
     * Actualiza el color asociado a una serie previamente registrada.
     *
     * @param idSerie       Identificador del registro de serie a actualizar.
     * @param idColorNuevo  Identificador del nuevo color.
     * @return DTO de la serie actualizada.
     * @throws EntityNotFoundException si no existe la serie o el color.
     */
    @Override
    public KitSerieResponseDTO actualizarSerie(Integer idSerie, Integer idColorNuevo) {
        KitSerie serie = serieRepo.findById(idSerie)
                .orElseThrow(() -> new EntityNotFoundException("Serie no encontrada"));

        DimColor nuevoColor = colorRepo.findById(idColorNuevo)
                .orElseThrow(() -> new EntityNotFoundException("Color no encontrado"));

        serie.setColor(nuevoColor);
        return kitSerieMapper.toDTO(serieRepo.save(serie));
    }

    /**
     * Elimina un registro de serie por su identificador.
     *
     * @param idSerie Identificador del registro a eliminar.
     * @throws EntityNotFoundException si el registro no existe.
     */
    @Override
    public void eliminarSerie(Integer idSerie) {
        if (!serieRepo.existsById(idSerie)) {
            throw new EntityNotFoundException("No existe serie con id: " + idSerie);
        }
        serieRepo.deleteById(idSerie);
    }
}
