package org.minimarket.minimarketbackendspring.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.CarritoTemporalDto;
import org.minimarket.minimarketbackendspring.services.interfaces.CarritoTemporalService;
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
 * Controlador REST para manejar operaciones del carrito temporal.
 */
@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = {"http://localhost:3000"})
public class CarritoTemporalController {

    @Autowired
    private CarritoTemporalService carritoService;

    @Autowired
    private PDFExportUtil pdfExportUtil;

    /**
     * Obtiene todos los items del carrito de un usuario.
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<CarritoTemporalDto>> getCarritoByUsuario(@PathVariable String idUsuario) {
        return ResponseEntity.ok(carritoService.findByUsuario(idUsuario));
    }

    /**
     * Agrega un producto al carrito.
     */
    @PostMapping("/agregar")
    public ResponseEntity<CarritoTemporalDto> agregarProducto(@RequestBody CarritoTemporalDto dto) {
        CarritoTemporalDto item = carritoService.agregarProductoAlCarrito(dto.getIdUsuario(), dto.getIdProducto(), dto.getCantidad());
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    /**
     * Actualiza la cantidad de un producto en el carrito.
     */
    @PutMapping("/actualizar")
    public ResponseEntity<CarritoTemporalDto> actualizarCantidad(
            @RequestParam String idUsuario,
            @RequestParam String idProducto,
            @RequestParam Long cantidad) {
        CarritoTemporalDto item = carritoService.actualizarCantidad(idUsuario, idProducto, cantidad);
        return ResponseEntity.ok(item);
    }

    /**
     * Elimina un producto del carrito.
     */
    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminarProducto(
            @RequestParam String idUsuario,
            @RequestParam String idProducto) {
        carritoService.eliminarProductoDelCarrito(idUsuario, idProducto);
        return ResponseEntity.ok().build();
    }

    /**
     * Vacía todo el carrito de un usuario.
     */
    @DeleteMapping("/usuario/{idUsuario}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable String idUsuario) {
        carritoService.vaciarCarrito(idUsuario);
        return ResponseEntity.ok().build();
    }

    /**
     * Cuenta items en el carrito del usuario.
     */
    @GetMapping("/usuario/{idUsuario}/count")
    public ResponseEntity<Long> countItems(@PathVariable String idUsuario) {
        return ResponseEntity.ok(carritoService.countByUsuario(idUsuario));
    }

    /**
     * Calcula el total del carrito.
     */
    @GetMapping("/usuario/{idUsuario}/total")
    public ResponseEntity<BigDecimal> calcularTotal(@PathVariable String idUsuario) {
        return ResponseEntity.ok(carritoService.calcularTotalCarrito(idUsuario));
    }

    /**
     * Verifica si un producto está en el carrito.
     */
    @GetMapping("/verificar")
    public ResponseEntity<Boolean> verificarProducto(
            @RequestParam String idUsuario,
            @RequestParam String idProducto) {
        return ResponseEntity.ok(carritoService.existeProductoEnCarrito(idUsuario, idProducto));
    }

    /**
     * Obtiene carrito con descuentos aplicados.
     */
    @GetMapping("/usuario/{idUsuario}/con-descuentos")
    public ResponseEntity<List<CarritoTemporalDto>> getCarritoConDescuentos(@PathVariable String idUsuario) {
        return ResponseEntity.ok(carritoService.findByUsuarioConDescuentos(idUsuario));
    }
    
    /**
     * Calcula el total del carrito CON descuentos.
     */
    @GetMapping("/usuario/{idUsuario}/total-con-descuentos")
    public ResponseEntity<BigDecimal> calcularTotalConDescuentos(@PathVariable String idUsuario) {
        return ResponseEntity.ok(carritoService.calcularTotalCarritoConDescuentos(idUsuario));
    }

    /**
        * abre en nueva pestaña el PDF de la boleta generada
     */

    @GetMapping("/boleta/{idUsuario}")
    public ResponseEntity<byte[]> generarBoletaBonita(@PathVariable String idUsuario) {
        try {
            String numeroTransaccion = "BOL-" + System.currentTimeMillis();
            List<CarritoTemporalDto> items = carritoService.findByUsuarioConDescuentos(idUsuario);
            
            if (items.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            byte[] pdfBytes = pdfExportUtil.exportarBoletaPDF(items, idUsuario, numeroTransaccion);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("inline")
                    .filename("boleta-" + numeroTransaccion + ".pdf")
                    .build());
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
                    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}