package org.minimarket.minimarketbackendspring.controllers;

import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.DetallePedidoDTO;
import org.minimarket.minimarketbackendspring.services.interfaces.DetallePedidoService;
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

import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/api/detalle-pedidos")
@CrossOrigin(origins = {"http://localhost:3000", "https://minimarket-frontend.vercel.app"})
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    /**
     * Obtiene una lista de todos los detalles de pedido.
     *
     * @return una lista de objetos DetallePedidoDTO
     */
    @GetMapping
    public ResponseEntity<List<DetallePedidoDTO>> getAllDetalles() {
        try {
            List<DetallePedidoDTO> detalles = detallePedidoService.findAll();
            return ResponseEntity.ok(detalles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtiene un detalle de pedido por su ID.
     *
     * @param id el identificador del detalle de pedido
     * @return el objeto DetallePedidoDTO correspondiente
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> getDetalleById(@PathVariable Long id) {
        try {
            DetallePedidoDTO detalle = detallePedidoService.findById(id);
            return ResponseEntity.ok(detalle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Crea un nuevo detalle de pedido.
     *
     * @param detallePedidoDTO el objeto DetallePedidoDTO a crear
     * @param idPedido el identificador del pedido
     * @param idProducto el identificador del producto
     * @return una respuesta HTTP 201 si se crea correctamente
     */
    @PostMapping
    public ResponseEntity<DetallePedidoDTO> createDetalle(
            @RequestBody DetallePedidoDTO detallePedidoDTO,
            @RequestParam Long idPedido,
            @RequestParam String idProducto) {
        try {
            DetallePedidoDTO savedDetalle = detallePedidoService.save(detallePedidoDTO, idPedido, idProducto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDetalle);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Actualiza un detalle de pedido existente.
     *
     * @param id el identificador del detalle a actualizar
     * @param detallePedidoDTO el objeto DetallePedidoDTO con los datos actualizados
     * @return el objeto DetallePedidoDTO actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> updateDetalle(
            @PathVariable Long id,
            @RequestBody DetallePedidoDTO detallePedidoDTO) {
        try {
            DetallePedidoDTO updatedDetalle = detallePedidoService.update(id, detallePedidoDTO);
            return ResponseEntity.ok(updatedDetalle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Elimina un detalle de pedido por su ID.
     *
     * @param id el identificador del detalle a eliminar
     * @return una respuesta HTTP 204 si se elimina correctamente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalle(@PathVariable Long id) {
        try {
            detallePedidoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Obtiene una lista de detalles asociados a un pedido específico.
     *
     * @param idPedido el identificador del pedido
     * @return una lista de objetos DetallePedidoDTO
     */
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<DetallePedidoDTO>> getDetallesByPedido(@PathVariable Long idPedido) {
        try {
            List<DetallePedidoDTO> detalles = detallePedidoService.findByPedidoId(idPedido);
            return ResponseEntity.ok(detalles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Obtiene una lista de detalles asociados a un producto específico.
     *
     * @param idProducto el identificador del producto
     * @return una lista de objetos DetallePedidoDTO
     */
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<DetallePedidoDTO>> getDetallesByProducto(@PathVariable String idProducto) {
        try {
            List<DetallePedidoDTO> detalles = detallePedidoService.findByProductoId(idProducto);
            return ResponseEntity.ok(detalles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Obtiene una lista de detalles asociados a un usuario específico.
     *
     * @param idUsuario el identificador del usuario
     * @return una lista de objetos DetallePedidoDTO
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<DetallePedidoDTO>> getDetallesByUsuario(@PathVariable String idUsuario) {
        try {
            List<DetallePedidoDTO> detalles = detallePedidoService.findByUsuario(idUsuario);
            return ResponseEntity.ok(detalles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Busca un detalle específico por pedido y producto.
     *
     * @param idPedido el identificador del pedido
     * @param idProducto el identificador del producto
     * @return el objeto DetallePedidoDTO correspondiente
     */
    @GetMapping("/pedido/{idPedido}/producto/{idProducto}")
    public ResponseEntity<DetallePedidoDTO> getDetalleByPedidoAndProducto(
            @PathVariable Long idPedido,
            @PathVariable String idProducto) {
        try {
            DetallePedidoDTO detalle = detallePedidoService.findByPedidoAndProducto(idPedido, idProducto);
            if (detalle != null) {
                return ResponseEntity.ok(detalle);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Agrega un producto al pedido (útil para carrito de compras).
     *
     * @param idPedido el identificador del pedido
     * @param idProducto el identificador del producto
     * @param cantidad la cantidad del producto a agregar
     * @return una respuesta HTTP 201 si se agrega correctamente
     */
    @PostMapping("/pedido/{idPedido}/producto/{idProducto}")
    public ResponseEntity<DetallePedidoDTO> agregarProductoAPedido(
            @PathVariable Long idPedido,
            @PathVariable String idProducto,
            @RequestParam Long cantidad) {
        try {
            DetallePedidoDTO detalle = detallePedidoService.agregarProductoAPedido(idPedido, idProducto, cantidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(detalle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Actualiza la cantidad de un producto en el pedido.
     *
     * @param idPedido el identificador del pedido
     * @param idProducto el identificador del producto
     * @param nuevaCantidad la nueva cantidad del producto
     * @return el objeto DetallePedidoDTO actualizado
     */
    @PutMapping("/pedido/{idPedido}/producto/{idProducto}/cantidad")
    public ResponseEntity<DetallePedidoDTO> actualizarCantidad(
            @PathVariable Long idPedido,
            @PathVariable String idProducto,
            @RequestParam Long nuevaCantidad) {
        try {
            DetallePedidoDTO detalle = detallePedidoService.actualizarCantidad(idPedido, idProducto, nuevaCantidad);
            return ResponseEntity.ok(detalle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Elimina un producto específico del pedido.
     *
     * @param idPedido el identificador del pedido
     * @param idProducto el identificador del producto
     * @return una respuesta HTTP 204 si se elimina correctamente
     */
    @DeleteMapping("/pedido/{idPedido}/producto/{idProducto}")
    public ResponseEntity<Void> eliminarProductoDePedido(
            @PathVariable Long idPedido,
            @PathVariable String idProducto) {
        try {
            detallePedidoService.eliminarProductoDePedido(idPedido, idProducto);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Elimina todos los detalles de un pedido específico.
     *
     * @param idPedido el identificador del pedido
     * @return una respuesta HTTP 204 si se eliminan correctamente
     */
    @DeleteMapping("/pedido/{idPedido}")
    public ResponseEntity<Void> eliminarTodosDetallesPorPedido(@PathVariable Long idPedido) {
        try {
            detallePedidoService.eliminarTodosDetallesPorPedido(idPedido);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Cuenta la cantidad de detalles en un pedido.
     *
     * @param idPedido el identificador del pedido
     * @return el número de items en el pedido
     */
    @GetMapping("/pedido/{idPedido}/count")
    public ResponseEntity<Long> countDetallesByPedido(@PathVariable Long idPedido) {
        try {
            Long count = detallePedidoService.countByPedidoId(idPedido);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Verifica si un producto específico existe en el pedido.
     *
     * @param idPedido el identificador del pedido
     * @param idProducto el identificador del producto
     * @return true si el producto existe en el pedido, false en caso contrario
     */
    @GetMapping("/pedido/{idPedido}/producto/{idProducto}/exists")
    public ResponseEntity<Boolean> existeProductoEnPedido(
            @PathVariable Long idPedido,
            @PathVariable String idProducto) {
        try {
            boolean existe = detallePedidoService.existeProductoEnPedido(idPedido, idProducto);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}