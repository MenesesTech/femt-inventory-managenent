package com.femt.inventory_management.controllers.kit;

import com.femt.inventory_management.dto.request.KitSerieBatchRequestDTO;
import com.femt.inventory_management.dto.response.KitSerieResponseDTO;
import com.femt.inventory_management.service.serie.imp.SerieColoresServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kit/serie")
public class SerieColoresController {
    private final SerieColoresServiceImp serieService;

    public SerieColoresController(SerieColoresServiceImp serieService) {
        this.serieService = serieService;
    }

    // GUARDAR SERIES
    @PostMapping("/guardar")
    public ResponseEntity<List<KitSerieResponseDTO>> guardarSerie(
            @RequestBody KitSerieBatchRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serieService.guardarSerie(requestDTO));
    }

    // LISTAR TODO
    @GetMapping("/listar")
    public ResponseEntity<List<KitSerieResponseDTO>> listarTodo() {
        return ResponseEntity.ok(serieService.listarTodo());
    }

    // LISTAR POR MODELO Y CATEGORÍA
    @GetMapping("/modelo/{idModelo}/categoria/{idCategoria}")
    public ResponseEntity<List<KitSerieResponseDTO>> listarPorModeloCategoria(
            @PathVariable Integer idModelo,
            @PathVariable Integer idCategoria) {
        return ResponseEntity.ok(
                serieService.listarPorModeloCategoria(idModelo, idCategoria));
    }

    // OBTENER TABLA ORGANIZADA
    @GetMapping("/tabla")
    public ResponseEntity<List<KitSerieResponseDTO>> tablaSeries(
            @RequestParam Integer idModelo,
            @RequestParam Integer idCategoria,
            @RequestParam Integer idSerieCode) {

        return ResponseEntity.ok(
                serieService.obtenerTablaSeries(idModelo, idCategoria, idSerieCode)
        );
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        serieService.eliminarSerie(id);
        return ResponseEntity.noContent().build();
    }
}
