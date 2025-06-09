package org.minimarket.minimarketbackendspring.services.interfaces;

import java.math.BigDecimal;
import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.CarritoTemporalDto;

public interface CarritoTemporalService {
    
    /**
     * Obtiene todos los items del carrito temporal
     */
    List<CarritoTemporalDto> findAll();
    
    /**
     * Busca un item por su ID
     */
    CarritoTemporalDto findById(Long id);
    
    /**
     * Crea un nuevo item en el carrito
     */
    CarritoTemporalDto save(CarritoTemporalDto carritoDTO, String idUsuario, String idProducto);
    
    /**
     * Actualiza un item existente
     */
    CarritoTemporalDto update(Long id, CarritoTemporalDto carritoDTO);
    
    /**
     * Elimina un item por su ID
     */
    void deleteById(Long id);
    
    /**
     * Busca items por usuario
     */
    List<CarritoTemporalDto> findByUsuario(String idUsuario);
    
    /**
     * Busca items por producto
     */
    List<CarritoTemporalDto> findByProducto(String idProducto);
    
    /**
     * Busca item específico por usuario y producto
     */
    CarritoTemporalDto findByUsuarioAndProducto(String idUsuario, String idProducto);
    
    /**
     * Agrega producto al carrito
     */
    CarritoTemporalDto agregarProductoAlCarrito(String idUsuario, String idProducto, Long cantidad);
    
    /**
     * Actualiza cantidad de producto en el carrito
     */
    CarritoTemporalDto actualizarCantidad(String idUsuario, String idProducto, Long nuevaCantidad);
    
    /**
     * Elimina producto del carrito
     */
    void eliminarProductoDelCarrito(String idUsuario, String idProducto);
    
    /**
     * Vacía todo el carrito de un usuario
     */
    void vaciarCarrito(String idUsuario);
    
    /**
     * Cuenta items en el carrito del usuario
     */
    Long countByUsuario(String idUsuario);
    
    /**
     * Verifica si existe producto en el carrito del usuario
     */
    boolean existeProductoEnCarrito(String idUsuario, String idProducto);
    
    /**
     * Verifica si el usuario tiene carrito
     */
    boolean tieneCarrito(String idUsuario);
    
    /*
     * Calcula el total del carrito
     */
    BigDecimal calcularTotalCarrito(String idUsuario);

        /**
     * Calcula el total del carrito aplicando descuentos disponibles
     */
    BigDecimal calcularTotalCarritoConDescuentos(String idUsuario);
    
    /**
     * Obtiene items del carrito con precios y descuentos aplicados
     */
    List<CarritoTemporalDto> findByUsuarioConDescuentos(String idUsuario);
    
}