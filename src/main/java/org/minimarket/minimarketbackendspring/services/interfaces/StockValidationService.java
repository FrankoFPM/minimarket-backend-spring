package org.minimarket.minimarketbackendspring.services.interfaces;

import java.util.List;
import java.util.Map;

import org.minimarket.minimarketbackendspring.dtos.DetallePedidoDTO;

/**
 * Servicio para validación y manejo de stock en tiempo real.
 * 
 * Este servicio se encarga de:
 * - Validar disponibilidad de stock al momento de la venta
 * - Limpiar carritos cuando productos no están disponibles
 * - Cancelar pedidos cuando hay conflictos de stock
 */
public interface StockValidationService {

  /**
   * Valida que todos los productos en el carrito tengan stock disponible.
   * 
   * @param idUsuario ID del usuario
   * @return Map con idProducto -> cantidad disponible (solo productos con
   *         problemas)
   */
  Map<String, Long> validarStockCarrito(String idUsuario);

  /**
   * Valida que todos los productos en el pedido tengan stock disponible.
   * 
   * @param detallesPedido Lista de detalles del pedido
   * @return Map con idProducto -> cantidad disponible (solo productos con
   *         problemas)
   */
  Map<String, Long> validarStockPedido(List<DetallePedidoDTO> detallesPedido);

  /**
   * Limpia productos sin stock de todos los carritos.
   * 
   * @param idProducto ID del producto que se agotó
   * @return Número de carritos afectados
   */
  int limpiarProductoDeCarritos(String idProducto);

  /**
   * Limpia productos con stock insuficiente del carrito de un usuario.
   * 
   * @param idUsuario         ID del usuario
   * @param stockInsuficiente Map con idProducto -> stock disponible
   * @return Lista de productos eliminados del carrito
   */
  List<String> limpiarCarritoPorStockInsuficiente(String idUsuario, Map<String, Long> stockInsuficiente);

  /**
   * Cancela pedidos que no pueden completarse por falta de stock.
   * 
   * @param idProducto ID del producto que se agotó
   * @return Número de pedidos cancelados
   */
  int cancelarPedidosPorStockInsuficiente(String idProducto);

  /**
   * Procesa la venta y actualiza el stock.
   * Valida stock -> Actualiza stock -> Limpia carritos afectados.
   * 
   * @param detallesPedido Lista de detalles del pedido
   * @return true si la venta se procesó exitosamente
   * @throws IllegalStateException si no hay stock suficiente
   */
  boolean procesarVentaYActualizarStock(List<DetallePedidoDTO> detallesPedido);

  /**
   * Verifica si un producto tiene stock suficiente.
   * 
   * @param idProducto         ID del producto
   * @param cantidadSolicitada Cantidad solicitada
   * @return true si hay stock suficiente
   */
  boolean tieneStockSuficiente(String idProducto, Long cantidadSolicitada);
}
