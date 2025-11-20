package com.femt.inventory_management.controllers.dimension;

import com.femt.inventory_management.dto.request.DimensionBatchRequestDTO;
import com.femt.inventory_management.dto.request.DimensionRequestDTO;
import com.femt.inventory_management.dto.response.DimensionResponseDTO;
import com.femt.inventory_management.service.dimension.imp.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dimension/categoria")
public class CategoriaController {

    private final CategoriaService dimCategoriaService;

    public CategoriaController(CategoriaService dimCategoriaService) {
        this.dimCategoriaService = dimCategoriaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<List<DimensionResponseDTO>> crear(@RequestBody DimensionBatchRequestDTO dto){
        List<DimensionResponseDTO> response = dimCategoriaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> actualizar(@PathVariable Integer id, @RequestBody DimensionRequestDTO dto){
        return ResponseEntity.ok(dimCategoriaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        dimCategoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DimensionResponseDTO> buscarPorId(@PathVariable Integer id){
        DimensionResponseDTO response = dimCategoriaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<DimensionResponseDTO> listarCategorias(){
        return dimCategoriaService.listarTodo();
    }

}
