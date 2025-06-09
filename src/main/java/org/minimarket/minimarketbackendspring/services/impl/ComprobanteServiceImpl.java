package org.minimarket.minimarketbackendspring.services.impl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.minimarket.minimarketbackendspring.dtos.ComprobanteDTO;
import org.minimarket.minimarketbackendspring.entities.Comprobante;
import org.minimarket.minimarketbackendspring.entities.Pedido;
import org.minimarket.minimarketbackendspring.repositories.ComprobanteRepository;
import org.minimarket.minimarketbackendspring.repositories.PedidoRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.ComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class ComprobanteServiceImpl implements ComprobanteService {

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ComprobanteDTO> findAll() {
        List<Comprobante> comprobantes = comprobanteRepository.findAll();
        return convertToDTOList(comprobantes);
    }

    @Override
    @Transactional(readOnly = true)
    public ComprobanteDTO findById(Long id) {
        Comprobante comprobante = comprobanteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comprobante no encontrado con ID: " + id));
        return convertToDTO(comprobante);
    }

    @Override
    public ComprobanteDTO save(ComprobanteDTO comprobanteDTO, Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + idPedido));

        // Validar que no exista ya un comprobante para este pedido
        if (comprobanteRepository.existsByIdPedido_Id(idPedido)) {
            throw new IllegalStateException("Ya existe un comprobante para el pedido con ID: " + idPedido);
        }

        Comprobante comprobante = convertToEntity(comprobanteDTO);
        comprobante.setIdPedido(pedido);

        // Establecer valores por defecto
        if (comprobante.getTipo() == null) {
            comprobante.setTipo("factura");
        }
        if (comprobante.getFecha() == null) {
            comprobante.setFecha(OffsetDateTime.now());
        }
        if (comprobante.getMontoTotal() == null) {
            comprobante.setMontoTotal(pedido.getTotal());
        }

        Comprobante savedComprobante = comprobanteRepository.save(comprobante);
        return convertToDTO(savedComprobante);
    }

    @Override
    public ComprobanteDTO update(Long id, ComprobanteDTO comprobanteDTO) {
        Comprobante existingComprobante = comprobanteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comprobante no encontrado con ID: " + id));

        // Solo permitir actualizar campos específicos
        if (comprobanteDTO.getTipo() != null) {
            existingComprobante.setTipo(comprobanteDTO.getTipo());
        }
        if (comprobanteDTO.getFecha() != null) {
            existingComprobante.setFecha(comprobanteDTO.getFecha());
        }

        // NO permitir cambios en monto total - debe coincidir con el pedido

        Comprobante updatedComprobante = comprobanteRepository.save(existingComprobante);
        return convertToDTO(updatedComprobante);
    }

    @Override
    public void deleteById(Long id) {
        if (!comprobanteRepository.existsById(id)) {
            throw new EntityNotFoundException("Comprobante no encontrado con ID: " + id);
        }
        comprobanteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComprobanteDTO> findByPedido(Long idPedido) {
        List<Comprobante> comprobantes = comprobanteRepository.findByIdPedido_Id(idPedido);
        return convertToDTOList(comprobantes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComprobanteDTO> findByUsuario(String idUsuario) {
        List<Comprobante> comprobantes = comprobanteRepository.findByIdPedido_IdUsuario_IdUsuario(idUsuario);
        return convertToDTOList(comprobantes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComprobanteDTO> findByTipo(String tipo) {
        List<Comprobante> comprobantes = comprobanteRepository.findByTipo(tipo);
        return convertToDTOList(comprobantes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComprobanteDTO> findByFechaRange(OffsetDateTime fechaInicio, OffsetDateTime fechaFin) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        List<Comprobante> comprobantes = comprobanteRepository.findByFechaBetween(fechaInicio, fechaFin);
        return convertToDTOList(comprobantes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComprobanteDTO> findByMontoRange(BigDecimal montoMinimo, BigDecimal montoMaximo) {
        if (montoMinimo.compareTo(montoMaximo) > 0) {
            throw new IllegalArgumentException("El monto mínimo no puede ser mayor al monto máximo");
        }

        List<Comprobante> comprobantes = comprobanteRepository.findByMontoTotalBetween(montoMinimo, montoMaximo);
        return convertToDTOList(comprobantes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComprobanteDTO> findByTipoAndUsuario(String tipo, String idUsuario) {
        List<Comprobante> comprobantes = comprobanteRepository.findByTipoAndIdPedido_IdUsuario_IdUsuario(tipo, idUsuario);
        return convertToDTOList(comprobantes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComprobanteDTO> findByUsuarioOrderByFecha(String idUsuario) {
        List<Comprobante> comprobantes = comprobanteRepository.findByIdPedido_IdUsuario_IdUsuarioOrderByFechaDesc(idUsuario);
        return convertToDTOList(comprobantes);
    }

    /**
     * Genera automáticamente un comprobante para un pedido.
     */
    @Override
    public ComprobanteDTO generarComprobanteAutomatico(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + idPedido));

        // Validar que no exista ya un comprobante
        if (comprobanteRepository.existsByIdPedido_Id(idPedido)) {
            throw new IllegalStateException("Ya existe un comprobante para este pedido");
        }

        Comprobante comprobante = new Comprobante();
        comprobante.setIdPedido(pedido);
        comprobante.setTipo("factura");
        comprobante.setFecha(OffsetDateTime.now());
        comprobante.setMontoTotal(pedido.getTotal());

        Comprobante savedComprobante = comprobanteRepository.save(comprobante);
        return convertToDTO(savedComprobante);
    }

    /**
     * Genera comprobante cuando el pedido se marca como pagado.
     */
    @Override
    public ComprobanteDTO generarComprobantePorPago(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + idPedido));

        // Validar que el pedido esté en estado que permita generar comprobante
        if (!"pagado".equals(pedido.getEstado()) && !"completado".equals(pedido.getEstado())) {
            throw new IllegalStateException("Solo se pueden generar comprobantes para pedidos pagados o completados");
        }

        return generarComprobanteAutomatico(idPedido);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeComprobantePorPedido(Long idPedido) {
        return comprobanteRepository.existsByIdPedido_Id(idPedido);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean tieneComprobantesPorUsuario(String idUsuario) {
        return comprobanteRepository.existsByIdPedido_IdUsuario_IdUsuario(idUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByTipo(String tipo) {
        return comprobanteRepository.countByTipo(tipo);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByUsuario(String idUsuario) {
        return comprobanteRepository.countByIdPedido_IdUsuario_IdUsuario(idUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularTotalMontosPorTipo(String tipo) {
        List<Comprobante> comprobantes = comprobanteRepository.findByTipo(tipo);
        
        return comprobantes.stream()
                .map(Comprobante::getMontoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional(readOnly = true)
    public ComprobanteDTO findUltimoComprobantePorUsuario(String idUsuario) {
        List<Comprobante> comprobantes = comprobanteRepository.findByIdPedido_IdUsuario_IdUsuarioOrderByFechaDesc(idUsuario);
        
        if (comprobantes.isEmpty()) {
            return null;
        }
        
        return convertToDTO(comprobantes.get(0));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComprobanteDTO> findByMontoMayorQue(BigDecimal monto) {
        List<Comprobante> comprobantes = comprobanteRepository.findByMontoTotalGreaterThan(monto);
        return convertToDTOList(comprobantes);
    }

    /**
     * Convierte entidad Comprobante a DTO incluyendo datos del pedido y usuario.
     */
    private ComprobanteDTO convertToDTO(Comprobante comprobante) {
        return new ComprobanteDTO(
                comprobante.getId(),
                comprobante.getIdPedido() != null ? comprobante.getIdPedido().getId() : null,
                comprobante.getIdPedido() != null && comprobante.getIdPedido().getIdUsuario() != null ? 
                    comprobante.getIdPedido().getIdUsuario().getIdUsuario() : null,
                comprobante.getIdPedido() != null && comprobante.getIdPedido().getIdUsuario() != null ? 
                    comprobante.getIdPedido().getIdUsuario().getNombre() : null,
                comprobante.getIdPedido() != null && comprobante.getIdPedido().getIdUsuario() != null ? 
                    comprobante.getIdPedido().getIdUsuario().getApellido() : null,
                comprobante.getIdPedido() != null ? comprobante.getIdPedido().getMetodoPago() : null,
                comprobante.getTipo(),
                comprobante.getFecha(),
                comprobante.getMontoTotal()
        );
    }

    /**
     * Convierte DTO a entidad Comprobante sin incluir datos del pedido o usuario.
     */
    private Comprobante convertToEntity(ComprobanteDTO dto) {
        Comprobante comprobante = new Comprobante();
        comprobante.setId(dto.getId());
        comprobante.setTipo(dto.getTipo());
        comprobante.setFecha(dto.getFecha());
        comprobante.setMontoTotal(dto.getMontoTotal());
        return comprobante;
    }

    private List<ComprobanteDTO> convertToDTOList(List<Comprobante> comprobantes) {
        return comprobantes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}