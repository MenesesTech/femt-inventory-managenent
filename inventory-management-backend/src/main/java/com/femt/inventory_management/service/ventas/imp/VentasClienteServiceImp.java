package com.femt.inventory_management.service.ventas.imp;

import com.femt.inventory_management.dto.request.VentasClienteRequestDTO;
import com.femt.inventory_management.dto.response.VentasClienteResponseDTO;
import com.femt.inventory_management.exceptions.VentasNotFoundException;
import com.femt.inventory_management.exceptions.VentasValidationException;
import com.femt.inventory_management.mapper.ventas.VentasMapper;
import com.femt.inventory_management.models.ventas.VentasCliente;
import com.femt.inventory_management.repository.VentasClienteRepository;
import com.femt.inventory_management.service.ventas.VentasClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VentasClienteServiceImp implements VentasClienteService {
    private final VentasClienteRepository clienteRepository;
    private final VentasMapper mapper;

    public VentasClienteServiceImp(VentasClienteRepository clienteRepository, VentasMapper mapper) {
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    /**
     * @param dto
     * @return
     */
    @Override
    public VentasClienteResponseDTO crear(VentasClienteRequestDTO dto) {
        validarCliente(dto);

        VentasCliente cliente = new VentasCliente();
        cliente.setNombreRazonSocial(dto.nombreRazonSocial());
        cliente.setApellido(dto.apellido());
        cliente.setDireccion(dto.direccion());
        cliente.setTelefono(dto.telefono());
        cliente.setRuc(dto.ruc());
        cliente.setDni(dto.dni());

        VentasCliente guardado = clienteRepository.save(cliente);
        log.info("Cliente creado con ID {}", guardado.getId());
        return mapper.toResponse(guardado);
    }

    /**
     * @param id
     * @param dto
     * @return
     */
    @Override
    public VentasClienteResponseDTO actualizar(Integer id, VentasClienteRequestDTO dto) {
        validarCliente(dto);

        VentasCliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new VentasNotFoundException("Cliente con ID " + id + " no encontrado"));

        cliente.setNombreRazonSocial(dto.nombreRazonSocial());
        cliente.setApellido(dto.apellido());
        cliente.setDireccion(dto.direccion());
        cliente.setTelefono(dto.telefono());
        cliente.setRuc(dto.ruc());
        cliente.setDni(dto.dni());

        VentasCliente actualizado = clienteRepository.save(cliente);
        log.info("Cliente {} actualizado", id);
        return mapper.toResponse(actualizado);
    }

    /**
     * @param id
     */
    @Override
    public void eliminar(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new VentasNotFoundException("Cliente con ID " + id + " no encontrado");
        }
        clienteRepository.deleteById(id);
        log.warn("Cliente {} eliminado", id);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public VentasClienteResponseDTO obtenerPorId(Integer id) {
        return clienteRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new VentasNotFoundException("Cliente con ID " + id + " no encontrado"));
    }

    /**
     * @return
     */
    @Override
    public List<VentasClienteResponseDTO> listar() {
        return clienteRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    private void validarCliente(VentasClienteRequestDTO dto) {
        if (dto.nombreRazonSocial() == null || dto.nombreRazonSocial().isBlank()) {
            throw new VentasValidationException("El nombre o razón social es obligatorio", "nombreRazonSocial");
        }
        if (dto.apellido() == null || dto.apellido().isBlank()) {
            throw new VentasValidationException("El apellido es obligatorio", "apellido");
        }
        if (dto.direccion() == null || dto.direccion().isBlank()) {
            throw new VentasValidationException("La dirección es obligatoria", "direccion");
        }
        if (dto.telefono() == null || dto.telefono().isBlank()) {
            throw new VentasValidationException("El teléfono es obligatorio", "telefono");
        }
        if (dto.ruc() == null || dto.ruc().isBlank()) {
            throw new VentasValidationException("El RUC es obligatorio", "ruc");
        }
        if (dto.dni() == null || dto.dni().isBlank()) {
            throw new VentasValidationException("El DNI es obligatorio", "dni");
        }
    }

}
