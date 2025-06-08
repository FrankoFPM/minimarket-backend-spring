package org.minimarket.minimarketbackendspring.services.impl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.minimarket.minimarketbackendspring.dtos.DetallePedidoDTO;
import org.minimarket.minimarketbackendspring.dtos.PedidoDTO;
import org.minimarket.minimarketbackendspring.entities.Pedido;
import org.minimarket.minimarketbackendspring.entities.Usuario;
import org.minimarket.minimarketbackendspring.repositories.PedidoRepository;
import org.minimarket.minimarketbackendspring.repositories.UsuarioRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.DetallePedidoService;
import org.minimarket.minimarketbackendspring.services.interfaces.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> findAll() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return convertToDTOList(pedidos);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + id));
        return convertToDTO(pedido);
    }

    @Override
    public PedidoDTO save(PedidoDTO pedidoDTO, String idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + idUsuario));

        Pedido pedido = convertToEntity(pedidoDTO);
        pedido.setIdUsuario(usuario);

        if (pedido.getEstado() == null) {
            pedido.setEstado("solicitado");
        }
        if (pedido.getMetodoPago() == null) {
            pedido.setMetodoPago("contraentrega");
        }
        if (pedido.getTotal() == null) {
            pedido.setTotal(BigDecimal.ZERO);
        }
        if (pedido.getDescuentoAplicado() == null) {
            pedido.setDescuentoAplicado(BigDecimal.ZERO);
        }
        if (pedido.getImpuesto() == null) {
            pedido.setImpuesto(BigDecimal.ZERO);
        }
        if (pedido.getFechaPedido() == null) {
            pedido.setFechaPedido(OffsetDateTime.now());
        }

        pedido.setCreatedAt(OffsetDateTime.now());
        pedido.setUpdatedAt(OffsetDateTime.now());
        pedido.setCreatedBy(usuario);
        pedido.setUpdatedBy(usuario);

        Pedido savedPedido = pedidoRepository.save(pedido);
        return convertToDTO(savedPedido);
    }

    @Override
    public PedidoDTO update(Long id, PedidoDTO pedidoDTO, String idUsuario) {
        Pedido existingPedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + id));

        if (pedidoDTO.getEstado() != null) {
            existingPedido.setEstado(pedidoDTO.getEstado());
        }
        if (pedidoDTO.getMetodoPago() != null) {
            existingPedido.setMetodoPago(pedidoDTO.getMetodoPago());
        }
        if (pedidoDTO.getTotal() != null) {
            existingPedido.setTotal(pedidoDTO.getTotal());
        }
        if (pedidoDTO.getDescuentoAplicado() != null) {
            existingPedido.setDescuentoAplicado(pedidoDTO.getDescuentoAplicado());
        }
        if (pedidoDTO.getImpuesto() != null) {
            existingPedido.setImpuesto(pedidoDTO.getImpuesto());
        }

        existingPedido.setUpdatedAt(OffsetDateTime.now());

        Usuario updatedByUser = usuarioRepository.findById(idUsuario).orElse(null);
        existingPedido.setUpdatedBy(updatedByUser);

        Pedido updatedPedido = pedidoRepository.save(existingPedido);
        return convertToDTO(updatedPedido);
    }

    @Override
    public void deleteById(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new EntityNotFoundException("Pedido no encontrado con ID: " + id);
        }
        pedidoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> findByUsuario(String idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + idUsuario);
        }

        List<Pedido> pedidos = pedidoRepository.findByIdUsuario_IdUsuario(idUsuario);
        return convertToDTOList(pedidos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> findByEstado(String estado) {
        List<Pedido> pedidos = pedidoRepository.findByEstado(estado);
        return convertToDTOList(pedidos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> findByEstadoAndUsuario(String estado, String idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + idUsuario);
        }

        List<Pedido> pedidos = pedidoRepository.findByEstadoAndIdUsuario_IdUsuario(estado, idUsuario);
        return convertToDTOList(pedidos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> findByFechaRange(OffsetDateTime fechaInicio, OffsetDateTime fechaFin) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        List<Pedido> pedidos = pedidoRepository.findByFechaPedidoBetween(fechaInicio, fechaFin);
        return convertToDTOList(pedidos);
    }

    @Override
    public PedidoDTO cambiarEstado(Long id, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + id));

        validarTransicionEstado(pedido.getEstado(), nuevoEstado);

        pedido.setEstado(nuevoEstado);
        pedido.setUpdatedAt(OffsetDateTime.now());

        Pedido updatedPedido = pedidoRepository.save(pedido);
        return convertToDTO(updatedPedido);
    }

    @Override
    public PedidoDTO crearPedidoDesdeCarrito(String idUsuario, String createdBy) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + idUsuario));

        if (existePedidoActivoParaUsuario(idUsuario)) {
            throw new IllegalStateException("El usuario ya tiene un pedido activo");
        }

        Pedido pedido = new Pedido();
        pedido.setIdUsuario(usuario);
        pedido.setEstado("solicitado");
        pedido.setMetodoPago("contraentrega");
        pedido.setTotal(BigDecimal.ZERO);
        pedido.setDescuentoAplicado(BigDecimal.ZERO);
        pedido.setImpuesto(BigDecimal.ZERO);
        pedido.setFechaPedido(OffsetDateTime.now());
        pedido.setCreatedAt(OffsetDateTime.now());
        pedido.setUpdatedAt(OffsetDateTime.now());

        if (createdBy != null) {
            Usuario createdByUser = usuarioRepository.findById(createdBy).orElse(null);
            pedido.setCreatedBy(createdByUser);
            pedido.setUpdatedBy(createdByUser);
        }

        Pedido savedPedido = pedidoRepository.save(pedido);
        
        actualizarTotalesPedido(savedPedido.getId());

        return convertToDTO(savedPedido);
    }

    @Override
    public void actualizarTotalesPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + idPedido));

        List<DetallePedidoDTO> detalles = detallePedidoService.findByPedidoId(idPedido);
        
        BigDecimal subtotal = detalles.stream()
                .map(DetallePedidoDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal impuesto = subtotal.multiply(BigDecimal.valueOf(0.19));
        
        BigDecimal total = subtotal
                .add(impuesto)
                .subtract(pedido.getDescuentoAplicado() != null ? pedido.getDescuentoAplicado() : BigDecimal.ZERO);
        
        pedido.setTotal(total);
        pedido.setImpuesto(impuesto);
        pedido.setUpdatedAt(OffsetDateTime.now());
        
        pedidoRepository.save(pedido);
    }

    @Override
    public void cancelarPedido(Long idPedido, String updatedBy) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + idPedido));

        if ("cancelado".equals(pedido.getEstado()) || "completado".equals(pedido.getEstado())) {
            throw new IllegalStateException("No se puede cancelar un pedido que ya está " + pedido.getEstado());
        }

        pedido.setEstado("cancelado");
        pedido.setUpdatedAt(OffsetDateTime.now());

        if (updatedBy != null) {
            Usuario updatedByUser = usuarioRepository.findById(updatedBy).orElse(null);
            pedido.setUpdatedBy(updatedByUser);
        }

        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoDTO findUltimoPedidoByUsuario(String idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + idUsuario);
        }

        List<Pedido> pedidos = pedidoRepository.findByIdUsuario_IdUsuarioOrderByFechaPedidoDesc(idUsuario);

        if (pedidos.isEmpty()) {
            return null;
        }

        return convertToDTO(pedidos.get(0));
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByEstado(String estado) {
        return pedidoRepository.countByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePedidoActivoParaUsuario(String idUsuario) {
        return pedidoRepository.existsByIdUsuario_IdUsuarioAndEstado(idUsuario, "solicitado")
                || pedidoRepository.existsByIdUsuario_IdUsuarioAndEstado(idUsuario, "pendiente_pago")
                || pedidoRepository.existsByIdUsuario_IdUsuarioAndEstado(idUsuario, "pagado");
    }

    private PedidoDTO convertToDTO(Pedido pedido) {
        return new PedidoDTO(
                pedido.getId(),
                pedido.getIdUsuario() != null ? pedido.getIdUsuario().getNombre() : null,
                pedido.getIdUsuario() != null ? pedido.getIdUsuario().getApellido() : null,
                pedido.getFechaPedido(),
                pedido.getEstado(),
                pedido.getMetodoPago(),
                pedido.getTotal(),
                pedido.getDescuentoAplicado(),
                pedido.getImpuesto(),
                pedido.getCreatedAt(),
                pedido.getUpdatedAt(),
                null
        );
    }

    private Pedido convertToEntity(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setId(dto.getId());
        pedido.setFechaPedido(dto.getFechaPedido());
        pedido.setEstado(dto.getEstado());
        pedido.setMetodoPago(dto.getMetodoPago());
        pedido.setTotal(dto.getTotal());
        pedido.setDescuentoAplicado(dto.getDescuentoAplicado());
        pedido.setImpuesto(dto.getImpuesto());
        pedido.setCreatedAt(dto.getCreatedAt());
        pedido.setUpdatedAt(dto.getUpdatedAt());
        return pedido;
    }

    private List<PedidoDTO> convertToDTOList(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private void validarTransicionEstado(String estadoActual, String nuevoEstado) {
        switch (estadoActual) {
            case "solicitado":
                if (!List.of("pendiente_pago", "cancelado").contains(nuevoEstado)) {
                    throw new IllegalArgumentException("Transición de estado inválida: " + estadoActual + " -> " + nuevoEstado);
                }
                break;
            case "pendiente_pago":
                if (!List.of("pagado", "cancelado").contains(nuevoEstado)) {
                    throw new IllegalArgumentException("Transición de estado inválida: " + estadoActual + " -> " + nuevoEstado);
                }
                break;
            case "pagado":
                if (!List.of("completado", "cancelado").contains(nuevoEstado)) {
                    throw new IllegalArgumentException("Transición de estado inválida: " + estadoActual + " -> " + nuevoEstado);
                }
                break;
            case "completado":
            case "cancelado":
                throw new IllegalArgumentException("No se puede cambiar el estado de un pedido " + estadoActual);
            default:
                throw new IllegalArgumentException("Estado desconocido: " + estadoActual);
        }
    }
}