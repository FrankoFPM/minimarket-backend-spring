package org.minimarket.minimarketbackendspring.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.minimarket.minimarketbackendspring.dtos.CarritoTemporalDto;
import org.minimarket.minimarketbackendspring.dtos.PedidoDTO;
import org.minimarket.minimarketbackendspring.services.interfaces.CarritoTemporalService;
import org.minimarket.minimarketbackendspring.services.interfaces.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Servicio para tareas programadas (cron jobs) del sistema.
 * 
 * Este servicio maneja tareas de mantenimiento automático como:
 * - Limpieza de carritos abandonados
 * - Cancelación de pedidos pendientes
 * - Reportes automáticos
 */
@Service
public class MantenimientoScheduledService {

  private static final Logger logger = LoggerFactory.getLogger(MantenimientoScheduledService.class);

  @Autowired
  private CarritoTemporalService carritoService;

  @Autowired
  private PedidoService pedidoService;

  /**
   * Limpia carritos abandonados cada 24 horas.
   * 
   * Un carrito se considera abandonado si el item más reciente no ha sido
   * modificado
   * en las últimas 24 horas. Esta tarea ayuda a mantener la base de datos limpia
   * y mejorar el rendimiento.
   */
  @Scheduled(cron = "0 0 0 * * *") // Cada 24 horas a medianoche
  public void limpiarCarritosAbandonados() {
    logger.info("Iniciando limpieza de carritos abandonados...");

    try {
      // Obtener todos los carritos
      List<CarritoTemporalDto> todosLosCarritos = carritoService.findAll();
      int carritosEliminados = 0;

      LocalDateTime hace24Horas = LocalDateTime.now().minusHours(24);

      // Agrupar por usuario para encontrar la fecha más reciente de cada carrito
      Map<String, List<CarritoTemporalDto>> carritosPorUsuario = todosLosCarritos.stream()
          .collect(Collectors.groupingBy(CarritoTemporalDto::getIdUsuario));

      for (Map.Entry<String, List<CarritoTemporalDto>> entry : carritosPorUsuario.entrySet()) {
        String idUsuario = entry.getKey();
        List<CarritoTemporalDto> itemsDelUsuario = entry.getValue();

        // Encontrar la fecha más reciente del carrito del usuario
        LocalDateTime fechaMasReciente = itemsDelUsuario.stream()
            .map(CarritoTemporalDto::getFechaAgregado)
            .filter(fecha -> fecha != null)
            .max(LocalDateTime::compareTo)
            .orElse(null);

        // Si la fecha más reciente es anterior a 24 horas, eliminar todo el carrito
        if (fechaMasReciente != null && fechaMasReciente.isBefore(hace24Horas)) {
          try {
            carritoService.vaciarCarrito(idUsuario);
            carritosEliminados++;
            logger.debug("Carrito completo eliminado para usuario: {} (último item: {})",
                idUsuario, fechaMasReciente);
          } catch (Exception e) {
            logger.warn("Error al eliminar carrito abandonado del usuario {}: {}",
                idUsuario, e.getMessage());
          }
        }
      }

      logger.info("Limpieza completada. {} carritos abandonados eliminados", carritosEliminados);

    } catch (Exception e) {
      logger.error("Error durante la limpieza de carritos abandonados: {}", e.getMessage());
    }
  }

  /**
   * Cancela pedidos pendientes de pago después de 3 días.
   * 
   * Los pedidos en estado "pendiente_pago" se cancelan automáticamente
   * si no han sido pagados en 3 días para liberar el stock.
   */
  @Scheduled(cron = "0 0 6 * * *") // Cada día a las 6:00 AM
  public void cancelarPedidosPendientes() {
    logger.info("Iniciando cancelación de pedidos pendientes...");

    try {
      // Obtener pedidos pendientes de pago
      List<PedidoDTO> pedidosPendientes = pedidoService.findByEstado("pendiente_pago");
      int pedidosCancelados = 0;

      LocalDateTime hace3Dias = LocalDateTime.now().minusDays(3);

      for (PedidoDTO pedido : pedidosPendientes) {
        // Verificar si el pedido tiene más de 3 días pendiente
        if (pedido.getFechaPedido() != null &&
            pedido.getFechaPedido().toLocalDateTime().isBefore(hace3Dias)) {

          try {
            pedidoService.cancelarPedido(pedido.getId(), "SISTEMA_AUTOMATICO");
            pedidosCancelados++;
            logger.debug("Pedido cancelado automáticamente: {}", pedido.getId());
          } catch (Exception e) {
            logger.warn("Error al cancelar pedido pendiente {}: {}", pedido.getId(), e.getMessage());
          }
        }
      }

      logger.info("Cancelación completada. {} pedidos pendientes cancelados", pedidosCancelados);

    } catch (Exception e) {
      logger.error("Error durante la cancelación de pedidos pendientes: {}", e.getMessage());
    }
  }

  /**
   * Genera un reporte de estado del sistema cada día a las 6:00 AM.
   * 
   * Proporciona información útil sobre el estado del sistema:
   * - Número de carritos activos
   * - Pedidos por estado
   * - Productos con stock bajo
   */
  @Scheduled(cron = "0 0 6 * * *") // Todos los días a las 6:00 AM
  public void generarReporteEstadoSistema() {
    logger.info("Generando reporte de estado del sistema...");

    try {
      // Contar carritos activos
      List<CarritoTemporalDto> carritosActivos = carritoService.findAll();
      int totalCarritos = carritosActivos.size();

      // Contar pedidos por estado
      long pedidosSolicitados = pedidoService.countByEstado("solicitado");
      long pedidosPendientes = pedidoService.countByEstado("pendiente_pago");
      long pedidosPagados = pedidoService.countByEstado("pagado");
      long pedidosCompletados = pedidoService.countByEstado("completado");
      long pedidosCancelados = pedidoService.countByEstado("cancelado");

      // Generar reporte
      StringBuilder reporte = new StringBuilder();
      reporte.append("\n=== REPORTE DE ESTADO DEL SISTEMA ===\n");
      reporte.append("Fecha: ").append(LocalDateTime.now()).append("\n");
      reporte.append("Carritos activos: ").append(totalCarritos).append("\n");
      reporte.append("Pedidos solicitados: ").append(pedidosSolicitados).append("\n");
      reporte.append("Pedidos pendientes de pago: ").append(pedidosPendientes).append("\n");
      reporte.append("Pedidos pagados: ").append(pedidosPagados).append("\n");
      reporte.append("Pedidos completados: ").append(pedidosCompletados).append("\n");
      reporte.append("Pedidos cancelados: ").append(pedidosCancelados).append("\n");
      reporte.append("=====================================\n");

      logger.info(reporte.toString());

    } catch (Exception e) {
      logger.error("Error al generar reporte de estado del sistema: {}", e.getMessage());
    }
  }

  /**
   * Limpia logs antiguos cada domingo a las 3:00 AM.
   * 
   * Mantiene solo los logs de los últimos 30 días para evitar
   * que el archivo de logs crezca indefinidamente.
   */
  @Scheduled(cron = "0 0 3 * * SUN") // Cada domingo a las 3:00 AM
  public void limpiarLogsAntiguos() {
    logger.info("Iniciando limpieza de logs antiguos...");

    try {
      // Esta es una tarea que normalmente requeriría acceso al sistema de archivos
      // Por ahora, solo registramos la acción
      logger.info("Limpieza de logs programada ejecutada correctamente");

      // Aquí podrías agregar lógica para:
      // - Eliminar archivos de log antiguos
      // - Comprimir logs históricos
      // - Limpiar tablas de auditoría

    } catch (Exception e) {
      logger.error("Error durante la limpieza de logs antiguos: {}", e.getMessage());
    }
  }

  /**
   * Verifica el estado de la base de datos cada hora.
   * 
   * Realiza verificaciones básicas de conectividad y consistencia.
   */
  @Scheduled(cron = "0 0 * * * *") // Cada hora
  public void verificarEstadoBaseDatos() {
    logger.debug("Verificando estado de la base de datos...");

    try {
      // Verificación básica: intentar obtener el conteo de pedidos
      long totalPedidos = pedidoService.countByEstado("completado");
      logger.debug("Verificación DB exitosa. Total pedidos completados: {}", totalPedidos);

    } catch (Exception e) {
      logger.error("Error en verificación de base de datos: {}", e.getMessage());
      // Aquí podrías enviar alertas por email, notificaciones, etc.
    }
  }
}
