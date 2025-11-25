package com.femt.inventory_management.service.ventas.imp;

import com.femt.inventory_management.dto.request.VentasPedidoRequestDTO;
import com.femt.inventory_management.dto.response.VentasPedidoResponseDTO;
import com.femt.inventory_management.exceptions.VentasNotFoundException;
import com.femt.inventory_management.exceptions.VentasValidationException;
import com.femt.inventory_management.mapper.ventas.VentasMapper;
import com.femt.inventory_management.models.kit.KitSerieManager;
import com.femt.inventory_management.models.seguridad.Usuario;
import com.femt.inventory_management.models.ventas.VentasCliente;
import com.femt.inventory_management.models.ventas.VentasPedido;
import com.femt.inventory_management.repository.KitSerieManagerRepository;
import com.femt.inventory_management.repository.SegUsuarioRepository;
import com.femt.inventory_management.repository.VentasClienteRepository;
import com.femt.inventory_management.repository.VentasPedidoRepository;
import com.femt.inventory_management.service.ventas.VentasPedidoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VentasPedidoServiceImp implements VentasPedidoService {
    private final VentasPedidoRepository pedidoRepository;
    private final VentasClienteRepository clienteRepository;
    private final SegUsuarioRepository usuarioRepository;
    private final KitSerieManagerRepository kitSerieManagerRepository;
    private final VentasMapper mapper;

    public VentasPedidoServiceImp(VentasPedidoRepository pedidoRepository, VentasClienteRepository clienteRepository, SegUsuarioRepository usuarioRepository, KitSerieManagerRepository kitSerieManagerRepository, VentasMapper mapper) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.kitSerieManagerRepository = kitSerieManagerRepository;
        this.mapper = mapper;
    }

    /**
     * @param dto
     * @return
     */
    @Override
    public VentasPedidoResponseDTO crear(VentasPedidoRequestDTO dto) {
        validar(dto);

        VentasCliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new VentasNotFoundException("Cliente con ID " + dto.idCliente() + " no encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new VentasNotFoundException("Usuario con ID " + dto.idUsuario() + " no encontrado"));

        KitSerieManager kitSerieManager = kitSerieManagerRepository.findById(dto.idKitSerieManager())
                .orElseThrow(() -> new VentasNotFoundException("KitSerieManager con ID " + dto.idKitSerieManager() + " no encontrado"));

        VentasPedido pedido = VentasPedido.builder()
                .cliente(cliente)
                .fechaCreada(dto.fechaCreada())
                .fechaEntrega(dto.fechaEntrega())
                .usuario(usuario)
                .kitSerieManager(kitSerieManager)
                .montoTotal(dto.montoTotal())
                .build();

        VentasPedido guardado = pedidoRepository.save(pedido);
        log.info("Pedido creado con ID {}", guardado.getId());
        return mapper.toResponse(guardado);
    }

    /**
     * @param id
     * @param dto
     * @return
     */
    @Override
    public VentasPedidoResponseDTO actualizar(Integer id, VentasPedidoRequestDTO dto) {
        validar(dto);

        VentasPedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new VentasNotFoundException("Pedido con ID " + id + " no encontrado"));

        VentasCliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new VentasNotFoundException("Cliente con ID " + dto.idCliente() + " no encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new VentasNotFoundException("Usuario con ID " + dto.idUsuario() + " no encontrado"));

        KitSerieManager kitSerieManager = kitSerieManagerRepository.findById(dto.idKitSerieManager())
                .orElseThrow(() -> new VentasNotFoundException("KitSerieManager con ID " + dto.idKitSerieManager() + " no encontrado"));

        pedido.setCliente(cliente);
        pedido.setFechaCreada(dto.fechaCreada());
        pedido.setFechaEntrega(dto.fechaEntrega());
        pedido.setUsuario(usuario);
        pedido.setKitSerieManager(kitSerieManager);
        pedido.setMontoTotal(dto.montoTotal());

        VentasPedido actualizado = pedidoRepository.save(pedido);
        log.info("Pedido {} actualizado", id);
        return mapper.toResponse(actualizado);
    }

    /**
     * @param id
     */
    @Override
    public void eliminar(Integer id) {
        if (!pedidoRepository.existsById(id)) {
            throw new VentasNotFoundException("Pedido con ID " + id + " no encontrado");
        }
        pedidoRepository.deleteById(id);
        log.warn("Pedido {} eliminado", id);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public VentasPedidoResponseDTO obtenerPorId(Integer id) {
        return pedidoRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new VentasNotFoundException("Pedido con ID " + id + " no encontrado"));
    }

    /**
     * @return
     */
    @Override
    public List<VentasPedidoResponseDTO> listar() {
        return pedidoRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    private void validar(VentasPedidoRequestDTO dto) {
        if (dto.idCliente() == null) {
            throw new VentasValidationException("El cliente es obligatorio", "idCliente");
        }
        if (dto.idUsuario() == null) {
            throw new VentasValidationException("El usuario es obligatorio", "idUsuario");
        }
        if (dto.idKitSerieManager() == null) {
            throw new VentasValidationException("El kit serie manager es obligatorio", "idKitSerieManager");
        }
        if (dto.fechaCreada() == null || dto.fechaEntrega() == null) {
            throw new VentasValidationException("Las fechas de creación y entrega son obligatorias", "fecha");
        }
        if (dto.montoTotal() == null) {
            throw new VentasValidationException("El monto total es obligatorio", "montoTotal");
        }
    }
}
