package org.minimarket.minimarketbackendspring.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.minimarket.minimarketbackendspring.entities.DescuentoPromocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentoPromocionRepository extends JpaRepository<DescuentoPromocion, Long> {
    
    // Buscar descuentos por producto
    List<DescuentoPromocion> findByIdProducto_IdProducto(String idProducto);
    
    // Buscar descuentos por estado
    List<DescuentoPromocion> findByEstado(String estado);
    
    // Buscar descuentos por rango de fechas
    List<DescuentoPromocion> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin);
    List<DescuentoPromocion> findByFechaFinBetween(LocalDate fechaInicio, LocalDate fechaFin);
    List<DescuentoPromocion> findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(LocalDate fechaActual1, LocalDate fechaActual2);
    
    // Buscar descuentos por porcentaje
    List<DescuentoPromocion> findByPorcentajeGreaterThan(BigDecimal porcentaje);
    List<DescuentoPromocion> findByPorcentajeLessThan(BigDecimal porcentaje);
    List<DescuentoPromocion> findByPorcentajeBetween(BigDecimal min, BigDecimal max);
    
    // Buscar descuentos ordenados
    List<DescuentoPromocion> findByEstadoOrderByFechaInicioDesc(String estado);
    List<DescuentoPromocion> findByIdProducto_IdProductoOrderByPorcentajeDesc(String idProducto);
    List<DescuentoPromocion> findByEstadoOrderByPorcentajeDesc(String estado);
    
    // Combinaciones específicas
    List<DescuentoPromocion> findByIdProducto_IdProductoAndEstado(String idProducto, String estado);
    List<DescuentoPromocion> findByEstadoAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(String estado, LocalDate fechaActual1, LocalDate fechaActual2);
    List<DescuentoPromocion> findByIdProducto_IdProductoAndEstadoAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(String idProducto, String estado, LocalDate fechaActual1, LocalDate fechaActual2);
    
    // El mejor descuento (mayor porcentaje)
    List<DescuentoPromocion> findByIdProducto_IdProductoAndEstadoAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByPorcentajeDesc(String idProducto, String estado, LocalDate fechaActual1, LocalDate fechaActual2);
    
    // Contadores y verificaciones
    Long countByIdProducto_IdProducto(String idProducto);
    Long countByEstado(String estado);
    Long countByIdProducto_IdProductoAndEstado(String idProducto, String estado);
    
    boolean existsByIdProducto_IdProductoAndEstado(String idProducto, String estado);
    boolean existsByIdProducto_IdProducto(String idProducto);
    boolean existsByEstado(String estado);
    
    // Eliminación
    void deleteByIdProducto_IdProducto(String idProducto);
    void deleteByEstado(String estado);
    void deleteByIdProducto_IdProductoAndEstado(String idProducto, String estado);
}