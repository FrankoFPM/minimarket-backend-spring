package org.minimarket.minimarketbackendspring.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.DescuentoPromocionDTO;
import org.minimarket.minimarketbackendspring.services.interfaces.DescuentoPromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * Controlador REST para manejar operaciones sobre descuentos y promociones.
 */
@RestController
@RequestMapping("/api/descuentos")
@CrossOrigin(origins = {"http://localhost:3000", "https://minimarket-frontend.vercel.app"})
public class DescuentoPromocionController {
    @Autowired
    // Inyecta el servicio de descuentos
    private DescuentoPromocionService descuentoService;
    public DescuentoPromocionController(DescuentoPromocionService descuentoService) {
        this.descuentoService = descuentoService;
    }

    /**
     * Obtiene todos los descuentos.
     */
    @GetMapping
    public ResponseEntity<List<DescuentoPromocionDTO>> getAllDescuentos() {
        return ResponseEntity.ok(descuentoService.findAll());
    }

    /**
     * Obtiene descuentos por producto.
     */
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<DescuentoPromocionDTO>> getDescuentosByProducto(@PathVariable String idProducto) {
        return ResponseEntity.ok(descuentoService.findByProducto(idProducto));
    }

    /**
     * Obtiene descuentos por estado.
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<DescuentoPromocionDTO>> getDescuentosByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(descuentoService.findByEstado(estado));
    }

    /**
     * Obtiene un descuento por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DescuentoPromocionDTO> getDescuentoById(@PathVariable Long id) {
        return ResponseEntity.ok(descuentoService.findById(id));
    }

    /**
     * Obtiene descuentos activos para un producto específico.
     */
    @GetMapping("/producto/{idProducto}/activos")
    public ResponseEntity<List<DescuentoPromocionDTO>> getDescuentosActivosParaProducto(@PathVariable String idProducto) {
        return ResponseEntity.ok(descuentoService.findDescuentosActivosParaProducto(idProducto));
    }

    /**
     * Obtiene todos los descuentos vigentes del sistema.
     */
    @GetMapping("/vigentes")
    public ResponseEntity<List<DescuentoPromocionDTO>> getDescuentosVigentes() {
        return ResponseEntity.ok(descuentoService.findDescuentosVigentes());
    }

    /**
     * Obtiene el mejor descuento para un producto.
     */
    @GetMapping("/producto/{idProducto}/mejor")
    public ResponseEntity<DescuentoPromocionDTO> getMejorDescuentoParaProducto(@PathVariable String idProducto) {
        DescuentoPromocionDTO descuento = descuentoService.findMejorDescuentoParaProducto(idProducto);
        if (descuento != null) {
            return ResponseEntity.ok(descuento);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Verifica si un producto tiene descuentos activos.
     */
    @GetMapping("/producto/{idProducto}/tiene-activos")
    public ResponseEntity<Boolean> tieneDescuentosActivos(@PathVariable String idProducto) {
        return ResponseEntity.ok(descuentoService.tieneDescuentosActivos(idProducto));
    }

    /**
     * Verifica si un descuento está vigente.
     */
    @GetMapping("/{id}/vigente")
    public ResponseEntity<Boolean> isDescuentoVigente(@PathVariable Long id) {
        return ResponseEntity.ok(descuentoService.isDescuentoVigente(id));
    }

    /**
     * Cuenta descuentos por producto.
     */
    @GetMapping("/count/producto/{idProducto}")
    public ResponseEntity<Long> countByProducto(@PathVariable String idProducto) {
        return ResponseEntity.ok(descuentoService.countByProducto(idProducto));
    }

    /**
     * Calcula el precio con descuento aplicado.
     */
    @GetMapping("/producto/{idProducto}/precio-con-descuento")
    public ResponseEntity<BigDecimal> calcularPrecioConDescuento(
            @PathVariable String idProducto,
            @RequestParam BigDecimal precioOriginal) {
        BigDecimal precioFinal = descuentoService.calcularPrecioConDescuento(idProducto, precioOriginal);
        return ResponseEntity.ok(precioFinal);
    }

    /**
     * Crea un nuevo descuento.
     *
     * @param descuento el objeto DescuentoPromocionDTO a crear
     * @param idProducto el identificador del producto
     * @return una respuesta HTTP 201 si se crea correctamente
     */
    @PostMapping
    public ResponseEntity<DescuentoPromocionDTO> createDescuento(
            @RequestBody DescuentoPromocionDTO descuento, 
            @RequestParam String idProducto) {        
        DescuentoPromocionDTO savedDescuento = descuentoService.save(descuento, idProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDescuento);
    }

    /**
     * Actualiza un descuento existente.
     *
     * @param id el identificador del descuento a actualizar
     * @param descuento el objeto DescuentoPromocionDTO con los datos actualizados
     * @return el objeto DescuentoPromocionDTO actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<DescuentoPromocionDTO> updateDescuento(
            @PathVariable Long id,
            @RequestBody DescuentoPromocionDTO descuento) {
        DescuentoPromocionDTO updatedDescuento = descuentoService.update(id, descuento);
        return ResponseEntity.ok(updatedDescuento);
    }

    /**
     * Activa un descuento.
     *
     * @param id el identificador del descuento
     * @return el objeto DescuentoPromocionDTO actualizado
     */
    @PatchMapping("/{id}/activar")
    public ResponseEntity<DescuentoPromocionDTO> activarDescuento(@PathVariable Long id) {
        DescuentoPromocionDTO descuento = descuentoService.activarDescuento(id);
        return ResponseEntity.ok(descuento);
    }

    /**
     * Desactiva un descuento.
     *
     * @param id el identificador del descuento
     * @return el objeto DescuentoPromocionDTO actualizado
     */
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<DescuentoPromocionDTO> desactivarDescuento(@PathVariable Long id) {
        DescuentoPromocionDTO descuento = descuentoService.desactivarDescuento(id);
        return ResponseEntity.ok(descuento);
    }

    /**
     * Elimina un descuento por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDescuento(@PathVariable Long id) {
        descuentoService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}