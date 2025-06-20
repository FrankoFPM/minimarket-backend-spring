package org.minimarket.minimarketbackendspring.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.minimarket.minimarketbackendspring.entities.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    
    // CORREGIDO: Usar nombres exactos de las propiedades de la entidad
    List<DetallePedido> findByIdPedido_Id(Long idPedido);
    
    // Buscar detalles por producto
    List<DetallePedido> findByIdProducto_IdProducto(String idProducto);
    
    // Buscar detalle específico por pedido y producto
    DetallePedido findByIdPedido_IdAndIdProducto_IdProducto(Long idPedido, String idProducto);
    
    // Buscar detalles por usuario (navegando por la relación)
    List<DetallePedido> findByIdPedido_IdUsuario_IdUsuario(String idUsuario);
    
    // Buscar detalles ordenados por ID
    List<DetallePedido> findByIdPedido_IdOrderById(Long idPedido);
    
    // Buscar detalles con cantidad mayor a un valor
    List<DetallePedido> findByCantidadGreaterThan(Long cantidad);
    
    // Buscar detalles con subtotal mayor a un valor
    List<DetallePedido> findBySubtotalGreaterThan(BigDecimal subtotal);
    
    // Contar detalles por pedido
    Long countByIdPedido_Id(Long idPedido);
    
    // Contar detalles por producto
    Long countByIdProducto_IdProducto(String idProducto);
    
    // Verificar si existe detalle para pedido y producto
    boolean existsByIdPedido_IdAndIdProducto_IdProducto(Long idPedido, String idProducto);
    
    // Verificar si existe detalle por pedido
    boolean existsByIdPedido_Id(Long idPedido);
    
    // Eliminar detalles por pedido
    void deleteByIdPedido_Id(Long idPedido);
}