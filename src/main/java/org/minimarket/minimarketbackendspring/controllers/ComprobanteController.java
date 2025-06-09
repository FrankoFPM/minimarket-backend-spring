package org.minimarket.minimarketbackendspring.controllers;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.ComprobanteDTO;
import org.minimarket.minimarketbackendspring.services.interfaces.ComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para manejar operaciones sobre comprobantes.
 */
@RestController
@RequestMapping("/api/comprobantes")
@CrossOrigin(origins = "*")
public class ComprobanteController {

    @Autowired
    private ComprobanteService comprobanteService;

    /**
     * Obtiene todos los comprobantes.
     */
    @GetMapping
    public ResponseEntity<List<ComprobanteDTO>> getAllComprobantes() {
        return ResponseEntity.ok(comprobanteService.findAll());
    }

    /**
     * Obtiene un comprobante por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ComprobanteDTO> getComprobanteById(@PathVariable Long id) {
        return ResponseEntity.ok(comprobanteService.findById(id));
    }

    /**
     * Obtiene comprobantes por pedido.
     */
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<ComprobanteDTO>> getComprobantesByPedido(@PathVariable Long idPedido) {
        return ResponseEntity.ok(comprobanteService.findByPedido(idPedido));
    }

    /**
     * Obtiene comprobantes por usuario.
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ComprobanteDTO>> getComprobantesByUsuario(@PathVariable String idUsuario) {
        return ResponseEntity.ok(comprobanteService.findByUsuario(idUsuario));
    }

    /**
     * Obtiene comprobantes por tipo.
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ComprobanteDTO>> getComprobantesByTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(comprobanteService.findByTipo(tipo));
    }

    /**
     * Obtiene comprobantes por usuario ordenados por fecha.
     */
    @GetMapping("/usuario/{idUsuario}/ordenados")
    public ResponseEntity<List<ComprobanteDTO>> getComprobantesByUsuarioOrdenados(@PathVariable String idUsuario) {
        return ResponseEntity.ok(comprobanteService.findByUsuarioOrderByFecha(idUsuario));
    }

    /**
     * Obtiene comprobantes por tipo y usuario.
     */
    @GetMapping("/tipo/{tipo}/usuario/{idUsuario}")
    public ResponseEntity<List<ComprobanteDTO>> getComprobantesByTipoAndUsuario(
            @PathVariable String tipo,
            @PathVariable String idUsuario) {
        return ResponseEntity.ok(comprobanteService.findByTipoAndUsuario(tipo, idUsuario));
    }

    /**
     * Obtiene comprobantes por rango de fechas.
     */
    @GetMapping("/fecha-range")
    public ResponseEntity<List<ComprobanteDTO>> getComprobantesByFechaRange(
            @RequestParam OffsetDateTime fechaInicio,
            @RequestParam OffsetDateTime fechaFin) {
        return ResponseEntity.ok(comprobanteService.findByFechaRange(fechaInicio, fechaFin));
    }

    /**
     * Obtiene comprobantes por rango de montos.
     */
    @GetMapping("/monto-range")
    public ResponseEntity<List<ComprobanteDTO>> getComprobantesByMontoRange(
            @RequestParam BigDecimal montoMinimo,
            @RequestParam BigDecimal montoMaximo) {
        return ResponseEntity.ok(comprobanteService.findByMontoRange(montoMinimo, montoMaximo));
    }

    /**
     * Obtiene comprobantes con monto mayor que el especificado.
     */
    @GetMapping("/monto-mayor/{monto}")
    public ResponseEntity<List<ComprobanteDTO>> getComprobantesByMontoMayor(@PathVariable BigDecimal monto) {
        return ResponseEntity.ok(comprobanteService.findByMontoMayorQue(monto));
    }

    /**
     * Obtiene el último comprobante de un usuario.
     */
    @GetMapping("/usuario/{idUsuario}/ultimo")
    public ResponseEntity<ComprobanteDTO> getUltimoComprobantePorUsuario(@PathVariable String idUsuario) {
        ComprobanteDTO comprobante = comprobanteService.findUltimoComprobantePorUsuario(idUsuario);
        if (comprobante != null) {
            return ResponseEntity.ok(comprobante);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Verifica si existe comprobante para un pedido.
     */
    @GetMapping("/existe/pedido/{idPedido}")
    public ResponseEntity<Boolean> existeComprobantePorPedido(@PathVariable Long idPedido) {
        return ResponseEntity.ok(comprobanteService.existeComprobantePorPedido(idPedido));
    }

    /**
     * Verifica si un usuario tiene comprobantes.
     */
    @GetMapping("/usuario/{idUsuario}/tiene-comprobantes")
    public ResponseEntity<Boolean> tieneComprobantesPorUsuario(@PathVariable String idUsuario) {
        return ResponseEntity.ok(comprobanteService.tieneComprobantesPorUsuario(idUsuario));
    }

    /**
     * Cuenta comprobantes por tipo.
     */
    @GetMapping("/count/tipo/{tipo}")
    public ResponseEntity<Long> countByTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(comprobanteService.countByTipo(tipo));
    }

    /**
     * Cuenta comprobantes por usuario.
     */
    @GetMapping("/count/usuario/{idUsuario}")
    public ResponseEntity<Long> countByUsuario(@PathVariable String idUsuario) {
        return ResponseEntity.ok(comprobanteService.countByUsuario(idUsuario));
    }

    /**
     * Calcula el total de montos por tipo de comprobante.
     */
    @GetMapping("/total-montos/tipo/{tipo}")
    public ResponseEntity<BigDecimal> calcularTotalMontosPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(comprobanteService.calcularTotalMontosPorTipo(tipo));
    }

    /**
     * Crea un nuevo comprobante.
     */
    @PostMapping
    public ResponseEntity<ComprobanteDTO> createComprobante(
            @RequestBody ComprobanteDTO comprobanteDTO,
            @RequestParam Long idPedido) {
        ComprobanteDTO savedComprobante = comprobanteService.save(comprobanteDTO, idPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComprobante);
    }

    /**
     * Genera automáticamente un comprobante para un pedido.
     */
    @PostMapping("/generar-automatico/{idPedido}")
    public ResponseEntity<ComprobanteDTO> generarComprobanteAutomatico(@PathVariable Long idPedido) {
        ComprobanteDTO comprobante = comprobanteService.generarComprobanteAutomatico(idPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(comprobante);
    }

    /**
     * Genera comprobante cuando el pedido se marca como pagado.
     */
    @PostMapping("/generar-por-pago/{idPedido}")
    public ResponseEntity<ComprobanteDTO> generarComprobantePorPago(@PathVariable Long idPedido) {
        ComprobanteDTO comprobante = comprobanteService.generarComprobantePorPago(idPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(comprobante);
    }

    /**
     * Actualiza un comprobante existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ComprobanteDTO> updateComprobante(
            @PathVariable Long id,
            @RequestBody ComprobanteDTO comprobanteDTO) {
        ComprobanteDTO updatedComprobante = comprobanteService.update(id, comprobanteDTO);
        return ResponseEntity.ok(updatedComprobante);
    }

    /**
     * Elimina un comprobante por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComprobante(@PathVariable Long id) {
        comprobanteService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}