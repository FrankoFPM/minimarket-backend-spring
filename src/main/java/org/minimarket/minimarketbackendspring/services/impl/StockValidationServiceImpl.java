package org.minimarket.minimarketbackendspring.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.minimarket.minimarketbackendspring.dtos.CarritoTemporalDto;
import org.minimarket.minimarketbackendspring.dtos.DetallePedidoDTO;
import org.minimarket.minimarketbackendspring.entities.Producto;
import org.minimarket.minimarketbackendspring.repositories.ProductoRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.CarritoTemporalService;
import org.minimarket.minimarketbackendspring.services.interfaces.StockValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementación del servicio de validación de stock.
 * 
 * Maneja la lógica de validación de stock en tiempo real,
 * limpieza de carritos y cancelación de pedidos.
 */
@Service
@Transactional
public class StockValidationServiceImpl implements StockValidationService {

  @Autowired
  private ProductoRepository productoRepository;

  @Autowired
  private CarritoTemporalService carritoService;

  @Override
  @Transactional(readOnly = true)
  public Map<String, Long> validarStockCarrito(String idUsuario) {
    Map<String, Long> problemasStock = new HashMap<>();

    List<CarritoTemporalDto> items = carritoService.findByUsuario(idUsuario);

    for (CarritoTemporalDto item : items) {
      if (!tieneStockSuficiente(item.getIdProducto(), item.getCantidad())) {
        Producto producto = productoRepository.findById(item.getIdProducto())
            .orElse(null);

        if (producto != null) {
          problemasStock.put(item.getIdProducto(), producto.getStock());
        } else {
          problemasStock.put(item.getIdProducto(), 0L);
        }
      }
    }

    return problemasStock;
  }

  @Override
  @Transactional(readOnly = true)
  public Map<String, Long> validarStockPedido(List<DetallePedidoDTO> detallesPedido) {
    Map<String, Long> problemasStock = new HashMap<>();

    for (DetallePedidoDTO detalle : detallesPedido) {
      if (!tieneStockSuficiente(detalle.getIdProducto(), detalle.getCantidad())) {
        Producto producto = productoRepository.findById(detalle.getIdProducto())
            .orElse(null);

        if (producto != null) {
          problemasStock.put(detalle.getIdProducto(), producto.getStock());
        } else {
          problemasStock.put(detalle.getIdProducto(), 0L);
        }
      }
    }

    return problemasStock;
  }

  @Override
  public int limpiarProductoDeCarritos(String idProducto) {
    // Obtener todos los carritos que contienen este producto
    List<CarritoTemporalDto> carritosAfectados = carritoService.findByProducto(idProducto);

    // Eliminar el producto de todos los carritos
    for (CarritoTemporalDto item : carritosAfectados) {
      carritoService.eliminarProductoDelCarrito(item.getIdUsuario(), idProducto);
    }

    return carritosAfectados.size();
  }

  @Override
  public List<String> limpiarCarritoPorStockInsuficiente(String idUsuario, Map<String, Long> stockInsuficiente) {
    List<String> productosEliminados = stockInsuficiente.entrySet().stream()
        .map(entry -> {
          String idProducto = entry.getKey();
          Long stockDisponible = entry.getValue();

          // Si no hay stock disponible, eliminar completamente
          if (stockDisponible <= 0) {
            carritoService.eliminarProductoDelCarrito(idUsuario, idProducto);
            return idProducto;
          } else {
            // Si hay stock pero insuficiente, ajustar cantidad
            try {
              carritoService.actualizarCantidad(idUsuario, idProducto, stockDisponible);
              return null; // No eliminado, solo ajustado
            } catch (Exception e) {
              // Si hay error al ajustar, eliminar
              carritoService.eliminarProductoDelCarrito(idUsuario, idProducto);
              return idProducto;
            }
          }
        })
        .filter(idProducto -> idProducto != null)
        .collect(Collectors.toList());

    return productosEliminados;
  }

  @Override
  public int cancelarPedidosPorStockInsuficiente(String idProducto) {
    // TODO: Implementar cancelación de pedidos pendientes
    // Esto requiere acceso al servicio de pedidos y un estado de "pendiente"
    // Por ahora retornamos 0 hasta que se implemente la lógica de pedidos
    return 0;
  }

  @Override
  public boolean procesarVentaYActualizarStock(List<DetallePedidoDTO> detallesPedido) {
    // 1. Validar que todos los productos tengan stock suficiente
    Map<String, Long> problemasStock = validarStockPedido(detallesPedido);

    if (!problemasStock.isEmpty()) {
      StringBuilder mensaje = new StringBuilder("Stock insuficiente para: ");
      problemasStock.forEach((idProducto, stockDisponible) -> {
        mensaje.append(idProducto).append(" (disponible: ").append(stockDisponible).append("), ");
      });
      throw new IllegalStateException(mensaje.toString());
    }

    // 2. Actualizar stock de todos los productos
    try {
      for (DetallePedidoDTO detalle : detallesPedido) {
        actualizarStockProducto(detalle.getIdProducto(), detalle.getCantidad());
      }

      // 3. Limpiar carritos afectados por productos que se agotaron
      for (DetallePedidoDTO detalle : detallesPedido) {
        Producto producto = productoRepository.findById(detalle.getIdProducto())
            .orElse(null);

        if (producto != null && producto.getStock() <= 0) {
          limpiarProductoDeCarritos(detalle.getIdProducto());
        }
      }

      return true;

    } catch (Exception e) {
      // Si hay error, la transacción se revierte automáticamente
      throw new IllegalStateException("Error al procesar la venta: " + e.getMessage(), e);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public boolean tieneStockSuficiente(String idProducto, Long cantidadSolicitada) {
    Producto producto = productoRepository.findById(idProducto)
        .orElse(null);

    if (producto == null) {
      return false;
    }

    return producto.getStock() >= cantidadSolicitada;
  }

  /**
   * Método privado para actualizar el stock de un producto.
   * 
   * @param idProducto      ID del producto
   * @param cantidadVendida Cantidad a descontar del stock
   */
  private void actualizarStockProducto(String idProducto, Long cantidadVendida) {
    Producto producto = productoRepository.findById(idProducto)
        .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + idProducto));

    Long nuevoStock = producto.getStock() - cantidadVendida;

    if (nuevoStock < 0) {
      throw new IllegalStateException("Stock insuficiente para producto: " + idProducto);
    }

    producto.setStock(nuevoStock);
    productoRepository.save(producto);
  }
}
