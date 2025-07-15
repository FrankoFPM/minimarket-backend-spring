package org.minimarket.minimarketbackendspring.controllers;

import java.util.List;
import java.util.Map;

import org.minimarket.minimarketbackendspring.dtos.CarritoTemporalDto;
import org.minimarket.minimarketbackendspring.dtos.DetallePedidoDTO;
import org.minimarket.minimarketbackendspring.services.impl.CarritoTemporalServiceImpl;
import org.minimarket.minimarketbackendspring.services.interfaces.StockValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para manejo de stock y validaciones.
 * 
 * Proporciona endpoints para:
 * - Validar stock de carrito
 * - Procesar ventas
 * - Limpiar carritos
 */
@RestController
@RequestMapping("/api/stock")
public class StockController {

  @Autowired
  private StockValidationService stockValidationService;

  @Autowired
  private CarritoTemporalServiceImpl carritoService;

  /**
   * Valida el stock de todos los productos en el carrito de un usuario.
   * 
   * @param idUsuario ID del usuario
   * @return Map con productos problemáticos y stock disponible
   */
  @GetMapping("/validar-carrito/{idUsuario}")
  public ResponseEntity<Map<String, Long>> validarStockCarrito(@PathVariable String idUsuario) {
    try {
      Map<String, Long> problemasStock = stockValidationService.validarStockCarrito(idUsuario);

      if (problemasStock.isEmpty()) {
        return ResponseEntity.ok(problemasStock);
      } else {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemasStock);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Obtiene el carrito del usuario con validación automática de stock.
   * 
   * @param idUsuario ID del usuario
   * @return Lista de items del carrito válidos
   */
  @GetMapping("/carrito-validado/{idUsuario}")
  public ResponseEntity<List<CarritoTemporalDto>> obtenerCarritoValidado(@PathVariable String idUsuario) {
    try {
      List<CarritoTemporalDto> carrito = carritoService.findByUsuarioConValidacion(idUsuario);
      return ResponseEntity.ok(carrito);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Procesa una venta validando stock y actualizando inventario.
   * 
   * @param detallesPedido Lista de detalles del pedido
   * @return ResponseEntity con resultado del procesamiento
   */
  @PostMapping("/procesar-venta")
  public ResponseEntity<String> procesarVenta(@RequestBody List<DetallePedidoDTO> detallesPedido) {
    try {
      boolean exitoso = stockValidationService.procesarVentaYActualizarStock(detallesPedido);

      if (exitoso) {
        return ResponseEntity.ok("Venta procesada exitosamente");
      } else {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al procesar la venta");
      }
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error interno del servidor: " + e.getMessage());
    }
  }

  /**
   * Verifica si un producto tiene stock suficiente.
   * 
   * @param idProducto ID del producto
   * @param cantidad   Cantidad solicitada
   * @return true si hay stock suficiente
   */
  @GetMapping("/verificar/{idProducto}/{cantidad}")
  public ResponseEntity<Boolean> verificarStock(@PathVariable String idProducto, @PathVariable Long cantidad) {
    try {
      boolean disponible = stockValidationService.tieneStockSuficiente(idProducto, cantidad);
      return ResponseEntity.ok(disponible);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Limpia un producto específico de todos los carritos.
   * 
   * @param idProducto ID del producto a limpiar
   * @return Número de carritos afectados
   */
  @PostMapping("/limpiar-producto/{idProducto}")
  public ResponseEntity<Integer> limpiarProductoDeCarritos(@PathVariable String idProducto) {
    try {
      int carritosAfectados = stockValidationService.limpiarProductoDeCarritos(idProducto);
      return ResponseEntity.ok(carritosAfectados);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Valida y limpia el carrito de un usuario específico.
   * 
   * @param idUsuario ID del usuario
   * @return Map con productos problemáticos
   */
  @PostMapping("/validar-limpiar-carrito/{idUsuario}")
  public ResponseEntity<Map<String, Long>> validarYLimpiarCarrito(@PathVariable String idUsuario) {
    try {
      Map<String, Long> problemasStock = carritoService.validarYLimpiarCarrito(idUsuario);
      return ResponseEntity.ok(problemasStock);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
