package org.minimarket.minimarketbackendspring.repositories;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.minimarket.minimarketbackendspring.entities.Comprobante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Long> {
    
    // Métodos paginados
    Page<Comprobante> findByIdPedido_Id(Long idPedido, Pageable pageable);
    Page<Comprobante> findByIdPedido_IdUsuario_IdUsuario(String idUsuario, Pageable pageable);
    Page<Comprobante> findByTipo(String tipo, Pageable pageable);
    Page<Comprobante> findByFechaBetween(OffsetDateTime fechaInicio, OffsetDateTime fechaFin, Pageable pageable);
    Page<Comprobante> findByMontoTotalGreaterThan(BigDecimal montoTotal, Pageable pageable);
    Page<Comprobante> findByMontoTotalBetween(BigDecimal minMonto, BigDecimal maxMonto, Pageable pageable);
    Page<Comprobante> findByTipoAndIdPedido_IdUsuario_IdUsuario(String tipo, String idUsuario, Pageable pageable);
    
    // Métodos sin paginación
    List<Comprobante> findByIdPedido_Id(Long idPedido);
    List<Comprobante> findByIdPedido_IdUsuario_IdUsuario(String idUsuario);
    List<Comprobante> findByTipo(String tipo);
    List<Comprobante> findByFechaBetween(OffsetDateTime fechaInicio, OffsetDateTime fechaFin);
    List<Comprobante> findByMontoTotalGreaterThan(BigDecimal montoTotal);
    List<Comprobante> findByMontoTotalBetween(BigDecimal minMonto, BigDecimal maxMonto);
    List<Comprobante> findByTipoAndIdPedido_IdUsuario_IdUsuario(String tipo, String idUsuario);
    
    // Métodos ordenados
    List<Comprobante> findByIdPedido_IdUsuario_IdUsuarioOrderByFechaDesc(String idUsuario);
    List<Comprobante> findByTipoOrderByFechaDesc(String tipo);
    List<Comprobante> findByIdPedido_IdOrderByFechaDesc(Long idPedido);
    
    // Contadores y verificaciones
    Long countByTipo(String tipo);
    Long countByIdPedido_IdUsuario_IdUsuario(String idUsuario);
    Long countByIdPedido_Id(Long idPedido);
    
    boolean existsByIdPedido_Id(Long idPedido);
    boolean existsByIdPedido_IdUsuario_IdUsuario(String idUsuario);
    boolean existsByTipo(String tipo);
    
    // Eliminación
    void deleteByIdPedido_Id(Long idPedido);
    void deleteByTipo(String tipo);
    void deleteByIdPedido_IdUsuario_IdUsuario(String idUsuario);
}