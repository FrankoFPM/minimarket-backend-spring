package org.minimarket.minimarketbackendspring.services.interfaces;

import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.ProductoDTO;

/**
 * Interfaz para definir las operaciones relacionadas con la entidad Producto.
 */
public interface ProductoService {

    /**
     * Obtiene una lista de todos los productos.
     *
     * @return
     */
    List<ProductoDTO> findAll();

    /**
     * Busca un producto por su ID.
     *
     * @param id el identificador del producto
     * @return
     */
    ProductoDTO findById(String id);

    /**
     * Busca un producto por su nombre.
     *
     * @param nombre el nombre del producto
     * @return
     */
    ProductoDTO findByNombre(String nombre);

    /**
     * Guarda un nuevo producto.
     *
     * @param producto
     */
    void save(ProductoDTO producto);

    /**
     * Actualiza un producto existente.
     *
     * @param producto
     */
    void update(ProductoDTO producto);

    /**
     * Elimina un producto por su ID.
     *
     * @param id
     */
    void delete(String id);
}
