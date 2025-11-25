package com.femt.inventory_management.controllers.ventas;

import com.femt.inventory_management.dto.request.VentasPedidoDetalleRequestDTO;
import com.femt.inventory_management.dto.response.VentasPedidoDetalleResponseDTO;
import com.femt.inventory_management.service.ventas.VentasPedidoDetalleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/ventas/pedidos/detalles")
public class VentasPedidoDetalleController {
    private final VentasPedidoDetalleService detalleService;

    public VentasPedidoDetalleController(VentasPedidoDetalleService detalleService) {
        this.detalleService = detalleService;
    }

    @PostMapping
    public ResponseEntity<VentasPedidoDetalleResponseDTO> crear(@RequestBody VentasPedidoDetalleRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentasPedidoDetalleResponseDTO> actualizar(@PathVariable Integer id,
                                                                     @RequestBody VentasPedidoDetalleRequestDTO dto) {
        return ResponseEntity.ok(detalleService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        detalleService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentasPedidoDetalleResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(detalleService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<VentasPedidoDetalleResponseDTO>> listar() {
        return ResponseEntity.ok(detalleService.listar());
    }
}
