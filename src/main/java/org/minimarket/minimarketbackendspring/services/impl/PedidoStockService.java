package org.minimarket.minimarketbackendspring.services.impl;

import java.util.List;
import java.util.Map;

import org.minimarket.minimarketbackendspring.dtos.CarritoTemporalDto;
import org.minimarket.minimarketbackendspring.dtos.DetallePedidoDTO;
import org.minimarket.minimarketbackendspring.services.interfaces.CarritoTemporalService;
import org.minimarket.minimarketbackendspring.services.interfaces.DetallePedidoService;
import org.minimarket.minimarketbackendspring.services.interfaces.StockValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para manejar la integración entre pedidos y gestión de stock.
 * 
 * Este servicio actúa como intermediario entre el carrito temporal,
 * los pedidos y la validación/actualización de stock.
 */
@Service
@Transactional
public class PedidoStockService {

  @Autowired
  private StockValidationService stockValidationService;

  @Autowired
  private CarritoTemporalService carritoService;

  @Autowired
  private DetallePedidoService detallePedidoService;

  /**
   * Convierte un carrito temporal en un pedido validando stock.
   * 
   * @param idUsuario ID del usuario
   * @param idPedido  ID del pedido creado
   * @return true si la conversión fue exitosa
   */
  public boolean convertirCarritoAPedido(String idUsuario, Long idPedido) {
    try {
      // 1. Obtener items del carrito
      List<CarritoTemporalDto> itemsCarrito = carritoService.findByUsuario(idUsuario);

      if (itemsCarrito.isEmpty()) {
        throw new IllegalStateException("El carrito está vacío");
      }

      // 2. Validar stock para todos los productos
      Map<String, Long> problemasStock = stockValidationService.validarStockCarrito(idUsuario);

      if (!problemasStock.isEmpty()) {
        StringBuilder mensaje = new StringBuilder("Stock insuficiente para: ");
        problemasStock.forEach((idProducto, stockDisponible) -> {
          mensaje.append(idProducto).append(" (disponible: ").append(stockDisponible).append("), ");
        });
        throw new IllegalStateException(mensaje.toString());
      }

      // 3. Crear detalles del pedido
      for (CarritoTemporalDto item : itemsCarrito) {
        DetallePedidoDTO detalleDTO = new DetallePedidoDTO();
        detalleDTO.setCantidad(item.getCantidad());
        // El precio se establecerá automáticamente desde el producto

        detallePedidoService.save(detalleDTO, idPedido, item.getIdProducto());
      }

      // 4. Limpiar carrito temporal
      carritoService.vaciarCarrito(idUsuario);

      return true;

    } catch (Exception e) {
      throw new IllegalStateException("Error al convertir carrito a pedido: " + e.getMessage(), e);
    }
  }

  /**
   * Confirma la venta de un pedido, actualizando el stock.
   * 
   * @param idPedido ID del pedido
   * @return true si la venta se confirmó exitosamente
   */
  public boolean confirmarVentaPedido(Long idPedido) {
    try {
      // 1. Obtener detalles del pedido
      List<DetallePedidoDTO> detallesPedido = detallePedidoService.findByPedidoId(idPedido);

      if (detallesPedido.isEmpty()) {
        throw new IllegalStateException("El pedido no tiene detalles");
      }

      // 2. Procesar venta y actualizar stock
      boolean exitoso = stockValidationService.procesarVentaYActualizarStock(detallesPedido);

      if (exitoso) {
        // TODO: Aquí se podría actualizar el estado del pedido a "completado"
        System.out.println("Venta confirmada para pedido: " + idPedido);
        return true;
      } else {
        throw new IllegalStateException("Error al procesar la venta del pedido");
      }

    } catch (Exception e) {
      throw new IllegalStateException("Error al confirmar venta del pedido: " + e.getMessage(), e);
    }
  }

  /**
   * Cancela un pedido sin afectar el stock.
   * 
   * @param idPedido ID del pedido
   * @return true si el pedido se canceló exitosamente
   */
  public boolean cancelarPedido(Long idPedido) {
    try {
      // Como no se había actualizado el stock, no hay nada que devolver
      // Solo se podría actualizar el estado del pedido a "cancelado"

      // TODO: Actualizar estado del pedido a "cancelado"
      System.out.println("Pedido cancelado: " + idPedido);

      return true;

    } catch (Exception e) {
      throw new IllegalStateException("Error al cancelar pedido: " + e.getMessage(), e);
    }
  }

  /**
   * Valida si un pedido puede ser procesado (tiene stock suficiente).
   * 
   * @param idPedido ID del pedido
   * @return Map con productos problemáticos (vacío si todo está bien)
   */
  public Map<String, Long> validarPedidoParaProcesamiento(Long idPedido) {
    try {
      List<DetallePedidoDTO> detallesPedido = detallePedidoService.findByPedidoId(idPedido);
      return stockValidationService.validarStockPedido(detallesPedido);
    } catch (Exception e) {
      throw new IllegalStateException("Error al validar pedido: " + e.getMessage(), e);
    }
  }

  /**
   * Obtiene el carrito de un usuario con validación automática de stock.
   * 
   * @param idUsuario ID del usuario
   * @return Lista de items del carrito válidos
   */
  public List<CarritoTemporalDto> obtenerCarritoValidado(String idUsuario) {
    // Validar y limpiar carrito automáticamente
    Map<String, Long> problemasStock = stockValidationService.validarStockCarrito(idUsuario);

    if (!problemasStock.isEmpty()) {
      stockValidationService.limpiarCarritoPorStockInsuficiente(idUsuario, problemasStock);
    }

    return carritoService.findByUsuario(idUsuario);
  }
}
