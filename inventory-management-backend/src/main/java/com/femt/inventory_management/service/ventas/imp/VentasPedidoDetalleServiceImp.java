package com.femt.inventory_management.service.ventas.imp;

import com.femt.inventory_management.dto.request.VentasPedidoDetalleRequestDTO;
import com.femt.inventory_management.dto.response.VentasPedidoDetalleResponseDTO;
import com.femt.inventory_management.exceptions.VentasNotFoundException;
import com.femt.inventory_management.exceptions.VentasValidationException;
import com.femt.inventory_management.mapper.ventas.VentasMapper;
import com.femt.inventory_management.models.ventas.VentasPedido;
import com.femt.inventory_management.models.ventas.VentasPedidoDetalle;
import com.femt.inventory_management.repository.VentasPedidoDetalleRepository;
import com.femt.inventory_management.repository.VentasPedidoRepository;
import com.femt.inventory_management.service.ventas.VentasPedidoDetalleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VentasPedidoDetalleServiceImp implements VentasPedidoDetalleService {
    private final VentasPedidoDetalleRepository detalleRepository;
    private final VentasPedidoRepository pedidoRepository;
    private final VentasMapper mapper;

    public VentasPedidoDetalleServiceImp(VentasPedidoDetalleRepository detalleRepository, VentasPedidoRepository pedidoRepository, VentasMapper mapper) {
        this.detalleRepository = detalleRepository;
        this.pedidoRepository = pedidoRepository;
        this.mapper = mapper;
    }

    /**
     * @param dto
     * @return
     */
    @Override
    public VentasPedidoDetalleResponseDTO crear(VentasPedidoDetalleRequestDTO dto) {
        validar(dto);

        VentasPedido pedido = pedidoRepository.findById(dto.idPedido())
                .orElseThrow(() -> new VentasNotFoundException("Pedido con ID " + dto.idPedido() + " no encontrado"));

        VentasPedidoDetalle detalle = VentasPedidoDetalle.builder()
                .pedido(pedido)
                .cantidadDocenas(dto.cantidadDocenas())
                .precioUnitario(dto.precioUnitario())
                .build();

        VentasPedidoDetalle guardado = detalleRepository.save(detalle);
        log.info("Detalle de pedido creado con ID {}", guardado.getId());
        return mapper.toResponse(guardado);
    }

    /**
     * @param id
     * @param dto
     * @return
     */
    @Override
    public VentasPedidoDetalleResponseDTO actualizar(Integer id, VentasPedidoDetalleRequestDTO dto) {
        validar(dto);

        VentasPedidoDetalle detalle = detalleRepository.findById(id)
                .orElseThrow(() -> new VentasNotFoundException("Detalle con ID " + id + " no encontrado"));

        VentasPedido pedido = pedidoRepository.findById(dto.idPedido())
                .orElseThrow(() -> new VentasNotFoundException("Pedido con ID " + dto.idPedido() + " no encontrado"));

        detalle.setPedido(pedido);
        detalle.setCantidadDocenas(dto.cantidadDocenas());
        detalle.setPrecioUnitario(dto.precioUnitario());

        VentasPedidoDetalle actualizado = detalleRepository.save(detalle);
        log.info("Detalle de pedido {} actualizado", id);
        return mapper.toResponse(actualizado);
    }

    /**
     * @param id
     */
    @Override
    public void eliminar(Integer id) {
        if (!detalleRepository.existsById(id)) {
            throw new VentasNotFoundException("Detalle con ID " + id + " no encontrado");
        }
        detalleRepository.deleteById(id);
        log.warn("Detalle de pedido {} eliminado", id);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public VentasPedidoDetalleResponseDTO obtenerPorId(Integer id) {
        return detalleRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new VentasNotFoundException("Detalle con ID " + id + " no encontrado"));
    }

    /**
     * @return
     */
    @Override
    public List<VentasPedidoDetalleResponseDTO> listar() {
        return detalleRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    private void validar(VentasPedidoDetalleRequestDTO dto) {
        if (dto.idPedido() == null) {
            throw new VentasValidationException("El pedido es obligatorio", "idPedido");
        }
        if (dto.cantidadDocenas() == null || dto.cantidadDocenas() <= 0) {
            throw new VentasValidationException("La cantidad de docenas debe ser mayor a cero", "cantidadDocenas");
        }
        if (dto.precioUnitario() == null) {
            throw new VentasValidationException("El precio unitario es obligatorio", "precioUnitario");
        }
    }
}
