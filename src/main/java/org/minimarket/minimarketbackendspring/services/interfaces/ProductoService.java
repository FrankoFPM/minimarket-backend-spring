package org.minimarket.minimarketbackendspring.services.interfaces;

import java.util.List;
import java.util.Optional;

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
    Optional<ProductoDTO> findByNombre(String nombre);

    /**
     * Obtiene una lista de productos asociados a una categoría específica.
     *
     * @param idCategoria el identificador de la categoría
     * @return una lista de objetos CategoriaDTO
     */
    List<ProductoDTO> findByCategoriaId(Long idCategoria);

    /**
     * Obtiene una lista de productos asociados a un proveedor específico.
     *
     * @param idProveedor el identificador del proveedor
     * @return una lista de objetos ProveedorDTO
     */
    List<ProductoDTO> findByProveedorId(Long idProveedor);

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

    /**
        * Obtiene productos con stock bajo.
        *
        * @return una lista de productos con stock bajo
    */
    List<ProductoDTO> findProductosConStockBajo();
}
