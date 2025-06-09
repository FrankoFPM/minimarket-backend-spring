package org.minimarket.minimarketbackendspring.services.interfaces;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.ComprobanteDTO;

public interface ComprobanteService {
    
    /**
     * Obtiene todos los comprobantes
     */
    List<ComprobanteDTO> findAll();
    
    /**
     * Busca un comprobante por su ID
     */
    ComprobanteDTO findById(Long id);
    
    /**
     * Crea un nuevo comprobante
     */
    ComprobanteDTO save(ComprobanteDTO comprobanteDTO, Long idPedido);
    
    /**
     * Actualiza un comprobante existente
     */
    ComprobanteDTO update(Long id, ComprobanteDTO comprobanteDTO);
    
    /**
     * Elimina un comprobante por su ID
     */
    void deleteById(Long id);
    
    /**
     * Busca comprobantes por pedido
     */
    List<ComprobanteDTO> findByPedido(Long idPedido);
    
    /**
     * Busca comprobantes por usuario
     */
    List<ComprobanteDTO> findByUsuario(String idUsuario);
    
    /**
     * Busca comprobantes por tipo
     */
    List<ComprobanteDTO> findByTipo(String tipo);
    
    /**
     * Busca comprobantes por rango de fechas
     */
    List<ComprobanteDTO> findByFechaRange(OffsetDateTime fechaInicio, OffsetDateTime fechaFin);
    
    /**
     * Busca comprobantes por rango de montos
     */
    List<ComprobanteDTO> findByMontoRange(BigDecimal montoMinimo, BigDecimal montoMaximo);
    
    /**
     * Busca comprobantes por tipo y usuario
     */
    List<ComprobanteDTO> findByTipoAndUsuario(String tipo, String idUsuario);
    
    /**
     * Obtiene comprobantes de un usuario ordenados por fecha
     */
    List<ComprobanteDTO> findByUsuarioOrderByFecha(String idUsuario);
    
    /**
     * Genera automáticamente un comprobante para un pedido
     */
    ComprobanteDTO generarComprobanteAutomatico(Long idPedido);
    
    /**
     * Genera comprobante cuando el pedido se marca como pagado
     */
    ComprobanteDTO generarComprobantePorPago(Long idPedido);
    
    /**
     * Verifica si existe comprobante para un pedido
     */
    boolean existeComprobantePorPedido(Long idPedido);
    
    /**
     * Verifica si un usuario tiene comprobantes
     */
    boolean tieneComprobantesPorUsuario(String idUsuario);
    
    /**
     * Cuenta comprobantes por tipo
     */
    Long countByTipo(String tipo);
    
    /**
     * Cuenta comprobantes por usuario
     */
    Long countByUsuario(String idUsuario);
    
    /**
     * Calcula el total de montos por tipo de comprobante
     */
    BigDecimal calcularTotalMontosPorTipo(String tipo);
    
    /**
     * Obtiene el último comprobante de un usuario
     */
    ComprobanteDTO findUltimoComprobantePorUsuario(String idUsuario);
    
    /**
     * Busca comprobantes por monto mayor que
     */
    List<ComprobanteDTO> findByMontoMayorQue(BigDecimal monto);
}