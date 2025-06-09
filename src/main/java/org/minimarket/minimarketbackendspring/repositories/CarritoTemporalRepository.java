package org.minimarket.minimarketbackendspring.repositories;

import java.time.Instant;
import java.util.List;

import org.minimarket.minimarketbackendspring.entities.CarritoTemporal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoTemporalRepository extends JpaRepository<CarritoTemporal, Long> {
    
    // Métodos paginados 
    Page<CarritoTemporal> findByIdUsuario_IdUsuario(String idUsuario, Pageable pageable);
    Page<CarritoTemporal> findByIdProducto_IdProducto(String idProducto, Pageable pageable);
    Page<CarritoTemporal> findByCantidadGreaterThan(Long cantidad, Pageable pageable);
    Page<CarritoTemporal> findByFechaAgregadoBetween(Instant fechaInicio, Instant fechaFin, Pageable pageable);
    
    // Métodos sin paginación
    List<CarritoTemporal> findByIdUsuario_IdUsuario(String idUsuario);
    List<CarritoTemporal> findByIdProducto_IdProducto(String idProducto);
    List<CarritoTemporal> findByCantidadGreaterThan(Long cantidad);
    
    // Métodos ordenados
    List<CarritoTemporal> findByIdUsuario_IdUsuarioOrderByFechaAgregadoDesc(String idUsuario);
    List<CarritoTemporal> findByIdUsuario_IdUsuarioOrderByFechaAgregadoAsc(String idUsuario);
    
    // Buscar item específico por usuario y producto
    CarritoTemporal findByIdUsuario_IdUsuarioAndIdProducto_IdProducto(String idUsuario, String idProducto);
    
    // Contadores y verificaciones
    Long countByIdUsuario_IdUsuario(String idUsuario);
    Long countByIdProducto_IdProducto(String idProducto);
    boolean existsByIdUsuario_IdUsuarioAndIdProducto_IdProducto(String idUsuario, String idProducto);
    boolean existsByIdUsuario_IdUsuario(String idUsuario);
    
    // Eliminación
    void deleteByIdUsuario_IdUsuario(String idUsuario);
    void deleteByIdUsuario_IdUsuarioAndIdProducto_IdProducto(String idUsuario, String idProducto);
}