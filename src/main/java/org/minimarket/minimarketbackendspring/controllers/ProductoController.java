package org.minimarket.minimarketbackendspring.controllers;

import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.ProductoDTO;
import org.minimarket.minimarketbackendspring.entities.Producto;
import org.minimarket.minimarketbackendspring.services.interfaces.ProductoService;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para manejar operaciones sobre Producto.
 * TODO: aplicar @Valid y manejo de errores
 */
@RestController
@RequestMapping("/api/producto")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    /**
     * Obtiene una lista de todos los productos.
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        return ResponseEntity.ok(productoService.findAll());
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id el identificador del producto
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable String id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    /**
     * Busca un producto por su nombre.
     *
     * @param nombre el nombre del producto
     * @return
     */
    @GetMapping("/producto/{nombre}")
    public ResponseEntity<ProductoDTO> getByNombre(@PathVariable String nombre) {
        return productoService.findByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo producto.
     *
     * @param producto el objeto ProductoDTO a crear
     * @return una respuesta HTTP 201 si se crea correctamente
     */
    @PostMapping
    public ResponseEntity<Void> createProducto(@RequestBody ProductoDTO producto) {
        productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Actualiza un producto existente.
     *
     * @param id el identificador del producto a actualizar
     * @param producto el objeto ProductoDTO con los datos actualizados
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProducto(@PathVariable String id, @RequestBody ProductoDTO producto) {
        producto.setId(id);
        productoService.update(producto);
        return ResponseEntity.ok().build();
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id el identificador del producto a eliminar
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable String id) {
        productoService.delete(id);
        return ResponseEntity.ok().build();
    }

}