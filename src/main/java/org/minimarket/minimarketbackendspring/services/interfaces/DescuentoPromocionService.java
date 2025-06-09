package org.minimarket.minimarketbackendspring.services.interfaces;

import java.math.BigDecimal;
import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.DescuentoPromocionDTO;

public interface DescuentoPromocionService {
    
    /**
     * Obtiene todos los descuentos
     */
    List<DescuentoPromocionDTO> findAll();
    
    /**
     * Busca un descuento por su ID
     */
    DescuentoPromocionDTO findById(Long id);
    
    /**
     * Crea un nuevo descuento
     */
    DescuentoPromocionDTO save(DescuentoPromocionDTO descuentoDTO, String idProducto);
    
    /**
     * Actualiza un descuento existente
     */
    DescuentoPromocionDTO update(Long id, DescuentoPromocionDTO descuentoDTO);
    
    /**
     * Elimina un descuento por su ID
     */
    void deleteById(Long id);
    
    /**
     * Busca descuentos por producto
     */
    List<DescuentoPromocionDTO> findByProducto(String idProducto);
    
    /**
     * Busca descuentos por estado
     */
    List<DescuentoPromocionDTO> findByEstado(String estado);
    
    /**
     * Busca descuentos activos para un producto
     */
    List<DescuentoPromocionDTO> findDescuentosActivosParaProducto(String idProducto);
    
    /**
     * Busca todos los descuentos vigentes
     */
    List<DescuentoPromocionDTO> findDescuentosVigentes();
    
    /**
     * Obtiene el mejor descuento para un producto
     */
    DescuentoPromocionDTO findMejorDescuentoParaProducto(String idProducto);
    
    /**
     * Calcula el precio con descuento aplicado
     */
    BigDecimal calcularPrecioConDescuento(String idProducto, BigDecimal precioOriginal);
    
    /**
     * Activa un descuento
     */
    DescuentoPromocionDTO activarDescuento(Long id);
    
    /**
     * Desactiva un descuento
     */
    DescuentoPromocionDTO desactivarDescuento(Long id);
    
    /**
     * Cuenta descuentos por producto
     */
    Long countByProducto(String idProducto);
    
    /**
     * Verifica si un producto tiene descuentos activos
     */
    boolean tieneDescuentosActivos(String idProducto);
    
    /**
     * Valida si un descuento está vigente
     */
    boolean isDescuentoVigente(Long idDescuento);
}