package com.femt.inventory_management.controllers.ventas;

import com.femt.inventory_management.dto.request.VentasClienteRequestDTO;
import com.femt.inventory_management.dto.response.VentasClienteResponseDTO;
import com.femt.inventory_management.service.ventas.VentasClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/ventas/clientes")
public class VentasClienteController {
    private final VentasClienteService clienteService;

    public VentasClienteController(VentasClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<VentasClienteResponseDTO> crear(@RequestBody VentasClienteRequestDTO dto) {
        log.info("Creando nuevo cliente");
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentasClienteResponseDTO> actualizar(@PathVariable Integer id,
                                                               @RequestBody VentasClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentasClienteResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<VentasClienteResponseDTO>> listar() {
        return ResponseEntity.ok(clienteService.listar());
    }
}
