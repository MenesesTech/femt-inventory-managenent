package com.femt.inventory_management.mapper.ventas;

import com.femt.inventory_management.dto.response.*;
import com.femt.inventory_management.models.ventas.*;
import org.springframework.stereotype.Component;

@Component
public class VentasMapper {

    public VentasClienteResponseDTO toResponse(VentasCliente cliente) {
        return new VentasClienteResponseDTO(
                cliente.getId(),
                cliente.getNombreRazonSocial(),
                cliente.getApellido(),
                cliente.getDireccion(),
                cliente.getTelefono(),
                cliente.getRuc(),
                cliente.getDni()
        );
    }

    public VentasPedidoResponseDTO toResponse(VentasPedido pedido) {
        return new VentasPedidoResponseDTO(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getFechaCreada(),
                pedido.getFechaEntrega(),
                pedido.getUsuario().getId(),
                pedido.getKitSerieManager().getId(),
                pedido.getMontoTotal()
        );
    }

    public VentasPedidoDetalleResponseDTO toResponse(VentasPedidoDetalle detalle) {
        return new VentasPedidoDetalleResponseDTO(
                detalle.getId(),
                detalle.getPedido().getId(),
                detalle.getCantidadDocenas(),
                detalle.getPrecioUnitario()
        );
    }

    public VentasVentaResponseDTO toResponse(VentasVenta venta) {
        return new VentasVentaResponseDTO(
                venta.getId(),
                venta.getIgv(),
                venta.getDescuento(),
                venta.getMontoTotal(),
                venta.getFechaPago(),
                venta.getCliente().getId(),
                venta.getPedido().getId(),
                venta.getUsuario().getId()
        );
    }

    public VentasComprobantePagoResponseDTO toResponse(VentasComprobantePago comprobante) {
        return new VentasComprobantePagoResponseDTO(
                comprobante.getId(),
                comprobante.getTipoComprobante(),
                comprobante.getSerie(),
                comprobante.getFechaEmision(),
                comprobante.getEstadoSunat(),
                comprobante.getArchivoXml(),
                comprobante.getArchivoCdr(),
                comprobante.getVenta().getId()
        );
    }
}
