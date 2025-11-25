package com.femt.inventory_management.controllers.ventas;

import com.femt.inventory_management.dto.request.VentasComprobantePagoRequestDTO;
import com.femt.inventory_management.dto.response.VentasComprobantePagoResponseDTO;
import com.femt.inventory_management.service.ventas.VentasComprobantePagoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/ventas/comprobantes")
public class VentasComprobantePagoController {

    private final VentasComprobantePagoService comprobantePagoService;

    public VentasComprobantePagoController(VentasComprobantePagoService comprobantePagoService) {
        this.comprobantePagoService = comprobantePagoService;
    }

    @PostMapping
    public ResponseEntity<VentasComprobantePagoResponseDTO> crear(@RequestBody VentasComprobantePagoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comprobantePagoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentasComprobantePagoResponseDTO> actualizar(@PathVariable Integer id,
                                                                       @RequestBody VentasComprobantePagoRequestDTO dto) {
        return ResponseEntity.ok(comprobantePagoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        comprobantePagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentasComprobantePagoResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(comprobantePagoService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<VentasComprobantePagoResponseDTO>> listar() {
        return ResponseEntity.ok(comprobantePagoService.listar());
    }
}
