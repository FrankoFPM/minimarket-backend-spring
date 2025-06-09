package org.minimarket.minimarketbackendspring.repositories;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.minimarket.minimarketbackendspring.entities.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Métodos paginados (recomendados para producción)
    Page<Pedido> findByIdUsuario_IdUsuario(String idUsuario, Pageable pageable);
    Page<Pedido> findByEstado(String estado, Pageable pageable);
    Page<Pedido> findByMetodoPago(String metodoPago, Pageable pageable);
    Page<Pedido> findByFechaPedidoBetween(OffsetDateTime fechaInicio, OffsetDateTime fechaFin, Pageable pageable);
    Page<Pedido> findByEstadoAndIdUsuario_IdUsuario(String estado, String idUsuario, Pageable pageable);
    Page<Pedido> findByTotalGreaterThan(BigDecimal total, Pageable pageable);
    Page<Pedido> findByTotalBetween(BigDecimal minTotal, BigDecimal maxTotal, Pageable pageable);
    
    // Métodos sin paginación (solo para casos específicos)
    List<Pedido> findByIdUsuario_IdUsuario(String idUsuario);
    List<Pedido> findByEstado(String estado);
    List<Pedido> findByMetodoPago(String metodoPago);
    List<Pedido> findByFechaPedidoBetween(OffsetDateTime fechaInicio, OffsetDateTime fechaFin);
    List<Pedido> findByEstadoAndIdUsuario_IdUsuario(String estado, String idUsuario);
    List<Pedido> findByTotalGreaterThan(BigDecimal total);
    List<Pedido> findByTotalBetween(BigDecimal minTotal, BigDecimal maxTotal);
    
    // Métodos ordenados
    List<Pedido> findByIdUsuario_IdUsuarioOrderByFechaPedidoDesc(String idUsuario);
    List<Pedido> findByEstadoOrderByFechaPedidoDesc(String estado);
    
    // Contadores y verificaciones
    Long countByEstado(String estado);
    Long countByIdUsuario_IdUsuario(String idUsuario);
    boolean existsByIdUsuario_IdUsuarioAndEstado(String idUsuario, String estado);
}