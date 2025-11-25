package com.femt.inventory_management.service.ventas.imp;

import com.femt.inventory_management.dto.request.VentasComprobantePagoRequestDTO;
import com.femt.inventory_management.dto.response.VentasComprobantePagoResponseDTO;
import com.femt.inventory_management.exceptions.VentasNotFoundException;
import com.femt.inventory_management.exceptions.VentasValidationException;
import com.femt.inventory_management.mapper.ventas.VentasMapper;
import com.femt.inventory_management.models.ventas.VentasComprobantePago;
import com.femt.inventory_management.models.ventas.VentasVenta;
import com.femt.inventory_management.repository.VentasComprobantePagoRepository;
import com.femt.inventory_management.repository.VentasVentaRepository;
import com.femt.inventory_management.service.ventas.VentasComprobantePagoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VentasComprobantePagoServiceImp implements VentasComprobantePagoService {
    private final VentasComprobantePagoRepository comprobantePagoRepository;
    private final VentasVentaRepository ventaRepository;
    private final VentasMapper mapper;

    public VentasComprobantePagoServiceImp(VentasComprobantePagoRepository comprobantePagoRepository,
                                           VentasVentaRepository ventaRepository,
                                           VentasMapper mapper) {
        this.comprobantePagoRepository = comprobantePagoRepository;
        this.ventaRepository = ventaRepository;
        this.mapper = mapper;
    }

    /**
     * @param dto
     * @return
     */
    @Override
    public VentasComprobantePagoResponseDTO crear(VentasComprobantePagoRequestDTO dto) {
        validar(dto);

        VentasVenta venta = ventaRepository.findById(dto.idVenta())
                .orElseThrow(() -> new VentasNotFoundException("Venta con ID " + dto.idVenta() + " no encontrada"));

        VentasComprobantePago comprobante = VentasComprobantePago.builder()
                .tipoComprobante(dto.tipoComprobante())
                .serie(dto.serie())
                .fechaEmision(dto.fechaEmision())
                .estadoSunat(dto.estadoSunat())
                .archivoXml(dto.archivoXml())
                .archivoCdr(dto.archivoCdr())
                .venta(venta)
                .build();

        VentasComprobantePago guardado = comprobantePagoRepository.save(comprobante);
        log.info("Comprobante de pago creado con ID {}", guardado.getId());
        return mapper.toResponse(guardado);
    }

    /**
     * @param id
     * @param dto
     * @return
     */
    @Override
    public VentasComprobantePagoResponseDTO actualizar(Integer id, VentasComprobantePagoRequestDTO dto) {
        validar(dto);

        VentasComprobantePago comprobante = comprobantePagoRepository.findById(id)
                .orElseThrow(() -> new VentasNotFoundException("Comprobante con ID " + id + " no encontrado"));

        VentasVenta venta = ventaRepository.findById(dto.idVenta())
                .orElseThrow(() -> new VentasNotFoundException("Venta con ID " + dto.idVenta() + " no encontrada"));

        comprobante.setTipoComprobante(dto.tipoComprobante());
        comprobante.setSerie(dto.serie());
        comprobante.setFechaEmision(dto.fechaEmision());
        comprobante.setEstadoSunat(dto.estadoSunat());
        comprobante.setArchivoXml(dto.archivoXml());
        comprobante.setArchivoCdr(dto.archivoCdr());
        comprobante.setVenta(venta);

        VentasComprobantePago actualizado = comprobantePagoRepository.save(comprobante);
        log.info("Comprobante de pago {} actualizado", id);
        return mapper.toResponse(actualizado);
    }

    /**
     * @param id
     */
    @Override
    public void eliminar(Integer id) {
        if (!comprobantePagoRepository.existsById(id)) {
            throw new VentasNotFoundException("Comprobante con ID " + id + " no encontrado");
        }
        comprobantePagoRepository.deleteById(id);
        log.warn("Comprobante de pago {} eliminado", id);

    }

    /**
     * @param id
     * @return
     */
    @Override
    public VentasComprobantePagoResponseDTO obtenerPorId(Integer id) {
        return comprobantePagoRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new VentasNotFoundException("Comprobante con ID " + id + " no encontrado"));
    }

    /**
     * @return
     */
    @Override
    public List<VentasComprobantePagoResponseDTO> listar() {
        return comprobantePagoRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    private void validar(VentasComprobantePagoRequestDTO dto) {
        if (dto.tipoComprobante() == null || dto.tipoComprobante().isBlank()) {
            throw new VentasValidationException("El tipo de comprobante es obligatorio", "tipoComprobante");
        }
        if (dto.serie() == null || dto.serie().isBlank()) {
            throw new VentasValidationException("La serie es obligatoria", "serie");
        }
        if (dto.fechaEmision() == null) {
            throw new VentasValidationException("La fecha de emisión es obligatoria", "fechaEmision");
        }
        if (dto.estadoSunat() == null || dto.estadoSunat().isBlank()) {
            throw new VentasValidationException("El estado Sunat es obligatorio", "estadoSunat");
        }
        if (dto.archivoXml() == null || dto.archivoXml().isBlank()) {
            throw new VentasValidationException("El archivo XML es obligatorio", "archivoXml");
        }
        if (dto.idVenta() == null) {
            throw new VentasValidationException("La venta es obligatoria", "idVenta");
        }
    }
}
