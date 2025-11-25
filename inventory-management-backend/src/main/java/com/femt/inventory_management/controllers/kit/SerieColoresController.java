package com.femt.inventory_management.controllers.kit;

import com.femt.inventory_management.dto.request.KitSerieBatchRequestDTO;
import com.femt.inventory_management.dto.request.KitSerieColorBatchRequest;
import com.femt.inventory_management.dto.response.KitSerieColorResponseDTO;
import com.femt.inventory_management.dto.response.KitSerieResponseDTO;
import com.femt.inventory_management.service.serie.imp.SerieColoresServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kit/serie")
public class SerieColoresController {
    private final SerieColoresServiceImp serieColorService;

    public SerieColoresController(SerieColoresServiceImp serieColorService) {
        this.serieColorService = serieColorService;
    }

    // GUARDAR SERIES
    @PostMapping("/crear")
    public ResponseEntity<List<KitSerieColorResponseDTO>> guardarSerie(
            @RequestBody KitSerieColorBatchRequest requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serieColorService.guardarSerieColores(requestDTO));
    }

    // Buscar color por ID
    @GetMapping("/{id}")
    public ResponseEntity<KitSerieColorResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(serieColorService.obtenerPorId(id));
    }

    /**
     * Recupera la matriz completa según modelo, categoría y tipo de componente.
     *
     * @param idModelo id del modelo
     * @param idCategoria id de la categoría
     * @param idTipoComponente id del tipo de componente
     * @return lista de combinaciones coincidentes
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<KitSerieColorResponseDTO>> obtenerPorModeloCategoriaTipo(
            @RequestParam Integer idModelo,
            @RequestParam Integer idCategoria,
            @RequestParam Integer idTipoComponente) {

        return ResponseEntity.ok(
                serieColorService.obtenerPorModeloCategoriaTipo(
                        idModelo, idCategoria, idTipoComponente
                )
        );
    }

    // ACTUALIZAR COLOR
    @PatchMapping("/{id}/color")
    public ResponseEntity<KitSerieColorResponseDTO> actualizarColor(
            @PathVariable Integer id,
            @RequestParam Integer nuevoColorId) {

        return ResponseEntity.ok(
                serieColorService.actualizarColor(id, nuevoColorId)
        );
    }

    /**
     * Elimina todas las combinaciones asociadas a un modelo, categoría y tipo de componente.
     * Se utiliza antes de cargar una nueva matriz desde cero.
     *
     * @param idModelo id del modelo
     * @param idCategoria id de la categoría
     * @param idTipoComponente id del tipo de componente
     * @return mensaje confirmando la eliminación
     */
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarPorModeloCategoriaTipo(
            @RequestParam Integer idModelo,
            @RequestParam Integer idCategoria,
            @RequestParam Integer idTipoComponente) {

        serieColorService.eliminarPorModeloCategoriaTipo(idModelo, idCategoria, idTipoComponente);

        return ResponseEntity.ok("Series eliminadas correctamente");
    }
}
