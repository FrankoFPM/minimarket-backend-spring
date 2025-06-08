package org.minimarket.minimarketbackendspring.services.interfaces;

import java.time.OffsetDateTime;
import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.PedidoDTO;

public interface PedidoService {
    
    /**
     * Obtiene todos los pedidos
     */
    List<PedidoDTO> findAll();
    
    /**
     * Busca un pedido por su ID
     */
    PedidoDTO findById(Long id);
    
    /**
     * Crea un nuevo pedido
     */
    PedidoDTO save(PedidoDTO pedidoDTO, String idUsuario);
    
    /**
     * Actualiza un pedido existente
     */
    PedidoDTO update(Long id, PedidoDTO pedidoDTO, String idUsuario);
    
    /**
     * Elimina un pedido por su ID
     */
    void deleteById(Long id);
    
    /**
     * Busca pedidos por usuario
     */
    List<PedidoDTO> findByUsuario(String idUsuario);
    
    /**
     * Busca pedidos por estado
     */
    List<PedidoDTO> findByEstado(String estado);
    
    /**
     * Busca pedidos por estado y usuario
     */
    List<PedidoDTO> findByEstadoAndUsuario(String estado, String idUsuario);
    
    /**
     * Busca pedidos por rango de fechas
     */
    List<PedidoDTO> findByFechaRange(OffsetDateTime fechaInicio, OffsetDateTime fechaFin);
    
    /**
     * Cambia el estado de un pedido
     */
    PedidoDTO cambiarEstado(Long id, String nuevoEstado);
    
    /**
     * Crea pedido desde carrito temporal
     */
    PedidoDTO crearPedidoDesdeCarrito(String idUsuario, String createdBy);
    
    /**
     * Actualiza totales del pedido
     */
    void actualizarTotalesPedido(Long idPedido);
    
    /**
     * Cancela un pedido
     */
    void cancelarPedido(Long idPedido, String updatedBy);
    
    /**
     * Obtiene el último pedido de un usuario
     */
    PedidoDTO findUltimoPedidoByUsuario(String idUsuario);
    
    /**
     * Cuenta pedidos por estado
     */
    Long countByEstado(String estado);
    
    /**
     * Verifica si existe un pedido activo para el usuario
     */
    boolean existePedidoActivoParaUsuario(String idUsuario);
}