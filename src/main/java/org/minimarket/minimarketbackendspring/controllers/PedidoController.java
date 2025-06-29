package org.minimarket.minimarketbackendspring.controllers;

import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.DetallePedidoDTO;
import org.minimarket.minimarketbackendspring.dtos.PedidoDTO;
import org.minimarket.minimarketbackendspring.services.interfaces.DetallePedidoService;
import org.minimarket.minimarketbackendspring.services.interfaces.PedidoService;
import org.minimarket.minimarketbackendspring.utils.PDFExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

import jakarta.persistence.EntityNotFoundException;

/**
 * Controlador REST para manejar operaciones sobre Pedido.
 */
@RestController
@RequestMapping("/api/pedido")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PDFExportUtil pdfExportUtil;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private DetallePedidoService detallePedidoService;

    /**
     * Obtiene todos los pedidos.
     */
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAllPedidos() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    /**
     * Obtiene pedidos por usuario.
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PedidoDTO>> getPedidosByUsuario(@PathVariable String idUsuario) {
        return ResponseEntity.ok(pedidoService.findByUsuario(idUsuario));
    }

    /**
     * Obtiene pedidos por estado.
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoDTO>> getPedidosByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(pedidoService.findByEstado(estado));
    }

    /**
     * Obtiene un pedido por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long id) {
        try {
            PedidoDTO pedido = pedidoService.findById(id);
            return ResponseEntity.ok(pedido);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene el último pedido de un usuario.
     */
    @GetMapping("/usuario/{idUsuario}/ultimo")
    public ResponseEntity<PedidoDTO> getUltimoPedidoByUsuario(@PathVariable String idUsuario) {
        PedidoDTO pedido = pedidoService.findUltimoPedidoByUsuario(idUsuario);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Verifica si un usuario tiene pedido activo.
     */
    @GetMapping("/usuario/{idUsuario}/activo")
    public ResponseEntity<Boolean> existePedidoActivo(@PathVariable String idUsuario) {
        return ResponseEntity.ok(pedidoService.existePedidoActivoParaUsuario(idUsuario));
    }

    /**
     * Cuenta pedidos por estado.
     *
     * @param estado el estado del pedido
     * @return
     */
    @GetMapping("/count/estado/{estado}")
    public ResponseEntity<Long> countByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(pedidoService.countByEstado(estado));
    }

    /**
     * Crea un nuevo pedido.
     *
     * @param pedido el objeto PedidoDTO a crear
     * @param idUsuario el identificador del usuario
     * @return una respuesta HTTP 201 si se crea correctamente
     */
    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody PedidoDTO pedido, @RequestParam String idUsuario) {
        PedidoDTO savedPedido = pedidoService.save(pedido, idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPedido);
    }

    /**
     * Crea un pedido desde el carrito temporal.
     *
     * @param idUsuario el identificador del usuario
     * @param createdBy el identificador de quien crea el pedido (opcional)
     * @return una respuesta HTTP 201 si se crea correctamente
     */
    @PostMapping("/desde-carrito")
    public ResponseEntity<PedidoDTO> createPedidoDesdeCarrito(
            @RequestParam String idUsuario,
            @RequestParam(required = false) String createdBy) {
        PedidoDTO pedido = pedidoService.crearPedidoDesdeCarrito(idUsuario, createdBy != null ? createdBy : idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    /**
     * Actualiza un pedido existente.
     *
     * @param id el identificador del pedido a actualizar
     * @param pedido el objeto PedidoDTO con los datos actualizados
     * @param idUsuario el identificador del usuario que actualiza
     * @return el objeto PedidoDTO actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(
            @PathVariable Long id,
            @RequestBody PedidoDTO pedido,
            @RequestParam String idUsuario) {
        PedidoDTO updatedPedido = pedidoService.update(id, pedido, idUsuario);
        return ResponseEntity.ok(updatedPedido);
    }

    /**
     * Cambia el estado de un pedido.
     *
     * @param id el identificador del pedido
     * @param nuevoEstado el nuevo estado del pedido
     * @return el objeto PedidoDTO actualizado
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoDTO> cambiarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        PedidoDTO pedido = pedidoService.cambiarEstado(id, nuevoEstado);
        return ResponseEntity.ok(pedido);
    }

    /**
     * Cancela un pedido.
     *
     * @param id el identificador del pedido
     * @param updatedBy el identificador de quien cancela el pedido
     * @return
     */
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id, @RequestParam String updatedBy) {
        pedidoService.cancelarPedido(id, updatedBy);
        return ResponseEntity.ok().build();
    }

    /**
     * Actualiza los totales de un pedido.
     *
     * @param id el identificador del pedido
     * @return
     */
    @PatchMapping("/{id}/totales")
    public ResponseEntity<Void> actualizarTotales(@PathVariable Long id) {
        pedidoService.actualizarTotalesPedido(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Elimina un pedido por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/boleta/{idBoleta}/{idUsuario}")
    public ResponseEntity<byte[]> generarBoletaBonita(@PathVariable String idBoleta,
                                                      @PathVariable String idUsuario) {
        try {
            String numeroTransaccion = "BOL-" + System.currentTimeMillis();
            List<DetallePedidoDTO> items = detallePedidoService.findByPedidoId(Long.parseLong(idBoleta));

            if (items.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            byte[] pdfBytes = pdfExportUtil.exportarBoletaFromPedidoPDF(items, idUsuario, numeroTransaccion);

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
