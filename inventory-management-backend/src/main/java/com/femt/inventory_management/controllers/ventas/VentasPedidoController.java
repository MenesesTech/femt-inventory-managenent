package com.femt.inventory_management.controllers.ventas;

import com.femt.inventory_management.dto.request.VentasPedidoRequestDTO;
import com.femt.inventory_management.dto.response.VentasPedidoResponseDTO;
import com.femt.inventory_management.service.ventas.VentasPedidoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/ventas/pedidos")
public class VentasPedidoController {
    private final VentasPedidoService pedidoService;

    public VentasPedidoController(VentasPedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<VentasPedidoResponseDTO> crear(@RequestBody VentasPedidoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentasPedidoResponseDTO> actualizar(@PathVariable Integer id,
                                                              @RequestBody VentasPedidoRequestDTO dto) {
        return ResponseEntity.ok(pedidoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentasPedidoResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<VentasPedidoResponseDTO>> listar() {
        return ResponseEntity.ok(pedidoService.listar());
    }
}
