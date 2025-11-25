package com.femt.inventory_management.controllers.ventas;

import com.femt.inventory_management.dto.request.VentasVentaRequestDTO;
import com.femt.inventory_management.dto.response.VentasVentaResponseDTO;
import com.femt.inventory_management.service.ventas.VentasVentaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/ventas/ventas")
public class VentasVentaController {
    private final VentasVentaService ventaService;

    public VentasVentaController(VentasVentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public ResponseEntity<VentasVentaResponseDTO> crear(@RequestBody VentasVentaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentasVentaResponseDTO> actualizar(@PathVariable Integer id,
                                                             @RequestBody VentasVentaRequestDTO dto) {
        return ResponseEntity.ok(ventaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentasVentaResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<VentasVentaResponseDTO>> listar() {
        return ResponseEntity.ok(ventaService.listar());
    }
}
