package com.femt.inventory_management.service.ventas.imp;

import com.femt.inventory_management.dto.request.VentasVentaRequestDTO;
import com.femt.inventory_management.dto.response.VentasVentaResponseDTO;
import com.femt.inventory_management.exceptions.VentasNotFoundException;
import com.femt.inventory_management.exceptions.VentasValidationException;
import com.femt.inventory_management.mapper.ventas.VentasMapper;
import com.femt.inventory_management.models.seguridad.Usuario;
import com.femt.inventory_management.models.ventas.VentasCliente;
import com.femt.inventory_management.models.ventas.VentasPedido;
import com.femt.inventory_management.models.ventas.VentasVenta;
import com.femt.inventory_management.repository.SegUsuarioRepository;
import com.femt.inventory_management.repository.VentasClienteRepository;
import com.femt.inventory_management.repository.VentasPedidoRepository;
import com.femt.inventory_management.repository.VentasVentaRepository;
import com.femt.inventory_management.service.ventas.VentasVentaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class VentasVentaServiceImp implements VentasVentaService {
    private final VentasVentaRepository ventaRepository;
    private final VentasClienteRepository clienteRepository;
    private final VentasPedidoRepository pedidoRepository;
    private final SegUsuarioRepository usuarioRepository;
    private final VentasMapper mapper;

    public VentasVentaServiceImp(VentasVentaRepository ventaRepository, VentasClienteRepository clienteRepository, VentasPedidoRepository pedidoRepository, SegUsuarioRepository usuarioRepository, VentasMapper mapper) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    /**
     * @param dto
     * @return
     */
    @Override
    public VentasVentaResponseDTO crear(VentasVentaRequestDTO dto) {
        validar(dto);

        VentasCliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new VentasNotFoundException("Cliente con ID " + dto.idCliente() + " no encontrado"));

        VentasPedido pedido = pedidoRepository.findById(dto.idPedido())
                .orElseThrow(() -> new VentasNotFoundException("Pedido con ID " + dto.idPedido() + " no encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new VentasNotFoundException("Usuario con ID " + dto.idUsuario() + " no encontrado"));

        VentasVenta venta = VentasVenta.builder()
                .igv(dto.igv())
                .descuento(dto.descuento())
                .montoTotal(dto.montoTotal())
                .fechaPago(dto.fechaPago())
                .cliente(cliente)
                .pedido(pedido)
                .usuario(usuario)
                .build();

        VentasVenta guardada = ventaRepository.save(venta);
        log.info("Venta creada con ID {}", guardada.getId());
        return mapper.toResponse(guardada);
    }

    /**
     * @param id
     * @param dto
     * @return
     */
    @Override
    public VentasVentaResponseDTO actualizar(Integer id, VentasVentaRequestDTO dto) {
        validar(dto);

        VentasVenta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new VentasNotFoundException("Venta con ID " + id + " no encontrada"));

        VentasCliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new VentasNotFoundException("Cliente con ID " + dto.idCliente() + " no encontrado"));

        VentasPedido pedido = pedidoRepository.findById(dto.idPedido())
                .orElseThrow(() -> new VentasNotFoundException("Pedido con ID " + dto.idPedido() + " no encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new VentasNotFoundException("Usuario con ID " + dto.idUsuario() + " no encontrado"));

        venta.setIgv(dto.igv());
        venta.setDescuento(dto.descuento());
        venta.setMontoTotal(dto.montoTotal());
        venta.setFechaPago(dto.fechaPago());
        venta.setCliente(cliente);
        venta.setPedido(pedido);
        venta.setUsuario(usuario);

        VentasVenta actualizada = ventaRepository.save(venta);
        log.info("Venta {} actualizada", id);
        return mapper.toResponse(actualizada);
    }

    /**
     * @param id
     */
    @Override
    public void eliminar(Integer id) {
        if (!ventaRepository.existsById(id)) {
            throw new VentasNotFoundException("Venta con ID " + id + " no encontrada");
        }
        ventaRepository.deleteById(id);
        log.warn("Venta {} eliminada", id);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public VentasVentaResponseDTO obtenerPorId(Integer id) {
        return ventaRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new VentasNotFoundException("Venta con ID " + id + " no encontrada"));
    }

    /**
     * @return
     */
    @Override
    public List<VentasVentaResponseDTO> listar() {
        return ventaRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    private void validar(VentasVentaRequestDTO dto) {
        if (dto.igv() == null) {
            throw new VentasValidationException("El IGV es obligatorio", "igv");
        }
        if (dto.montoTotal() == null) {
            throw new VentasValidationException("El monto total es obligatorio", "montoTotal");
        }
        if (dto.fechaPago() == null) {
            throw new VentasValidationException("La fecha de pago es obligatoria", "fechaPago");
        }
        if (dto.idCliente() == null) {
            throw new VentasValidationException("El cliente es obligatorio", "idCliente");
        }
        if (dto.idPedido() == null) {
            throw new VentasValidationException("El pedido es obligatorio", "idPedido");
        }
        if (dto.idUsuario() == null) {
            throw new VentasValidationException("El usuario es obligatorio", "idUsuario");
        }
    }
}
