package org.minimarket.minimarketbackendspring.services.interfaces;

import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.DetallePedidoDTO;

public interface DetallePedidoService {
    
    /**
     * Obtiene todos los detalles de pedido
     */
    List<DetallePedidoDTO> findAll();
    
    /**
     * Busca un detalle por su ID
     */
    DetallePedidoDTO findById(Long id);
    
    /**
     * Crea un nuevo detalle de pedido
     */
    DetallePedidoDTO save(DetallePedidoDTO detallePedidoDTO, Long idPedido, String idProducto);
    
    /**
     * Actualiza un detalle existente
     */
    DetallePedidoDTO update(Long id, DetallePedidoDTO detallePedidoDTO);
    
    /**
     * Elimina un detalle por su ID
     */
    void deleteById(Long id);
    
    /**
     * Busca detalles por pedido
     */
    List<DetallePedidoDTO> findByPedidoId(Long idPedido);
    
    /**
     * Busca detalles por producto
     */
    List<DetallePedidoDTO> findByProductoId(String idProducto);
    
    /**
     * Busca detalles por usuario
     */
    List<DetallePedidoDTO> findByUsuario(String idUsuario);
    
    /**
     * Busca detalle específico por pedido y producto
     */
    DetallePedidoDTO findByPedidoAndProducto(Long idPedido, String idProducto);
    
    /**
     * Agrega producto al pedido
     */
    DetallePedidoDTO agregarProductoAPedido(Long idPedido, String idProducto, Long cantidad);
    
    /**
     * Actualiza cantidad de producto en el pedido
     */
    DetallePedidoDTO actualizarCantidad(Long idPedido, String idProducto, Long nuevaCantidad);
    
    /**
     * Elimina producto del pedido
     */
    void eliminarProductoDePedido(Long idPedido, String idProducto);
    
    /**
     * Elimina todos los detalles de un pedido
     */
    void eliminarTodosDetallesPorPedido(Long idPedido);
    
    /**
     * Cuenta items por pedido
     */
    Long countByPedidoId(Long idPedido);
    
    /**
     * Verifica si producto existe en el pedido
     */
    boolean existeProductoEnPedido(Long idPedido, String idProducto);
}