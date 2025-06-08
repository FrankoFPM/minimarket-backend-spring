package org.minimarket.minimarketbackendspring.repositories;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.minimarket.minimarketbackendspring.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Buscar pedidos por usuario
    List<Pedido> findByIdUsuario_IdUsuario(String idUsuario);
    
    // Buscar pedidos por estado
    List<Pedido> findByEstado(String estado);
    
    // Buscar pedidos por método de pago
    List<Pedido> findByMetodoPago(String metodoPago);
    
    // Buscar pedidos en un rango de fechas
    List<Pedido> findByFechaPedidoBetween(OffsetDateTime fechaInicio, OffsetDateTime fechaFin);
    
    // Buscar pedidos por estado y usuario
    List<Pedido> findByEstadoAndIdUsuario_IdUsuario(String estado, String idUsuario);
    
    // Buscar pedidos con total mayor a un valor
    List<Pedido> findByTotalGreaterThan(BigDecimal total);
    
    // Buscar pedidos con total entre dos valores
    List<Pedido> findByTotalBetween(BigDecimal minTotal, BigDecimal maxTotal);
    
    // Contar pedidos por estado
    Long countByEstado(String estado);
    
    // Contar pedidos por usuario
    Long countByIdUsuario_IdUsuario(String idUsuario);
    
    // Buscar pedidos ordenados por fecha (más recientes primero)
    List<Pedido> findByIdUsuario_IdUsuarioOrderByFechaPedidoDesc(String idUsuario);
    
    // Buscar pedidos por estado ordenados por fecha
    List<Pedido> findByEstadoOrderByFechaPedidoDesc(String estado);
    
    // Verificar si existe pedido por usuario y estado
    boolean existsByIdUsuario_IdUsuarioAndEstado(String idUsuario, String estado);
}