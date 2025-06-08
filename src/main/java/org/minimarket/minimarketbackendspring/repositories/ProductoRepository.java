package org.minimarket.minimarketbackendspring.repositories;

import java.util.List;
import java.util.Optional;

import org.minimarket.minimarketbackendspring.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para manejar operaciones CRUD de la entidad Usuario.
 */
public interface ProductoRepository extends JpaRepository<Producto, String> {

    /**
     * Encuentra un producto por su nombre
     *
     * @param nombre nombre del producto
     * @return
     */
    Optional<Producto> findByNombre(String nombre); 
    
    /**
     * Encuentra todos los productos asociados a una categoría específica.
     *
     * @param idCategoria el identificador de la categoría
     * @return una lista de productos pertenecientes a la categoría
     */
    List<Producto> findByIdCategoria_Id(Long idCategoria);

     /**
     * Encuentra todos los productos asociados a un proveedor específico.
     *
     * @param idProveedor el identificador del proveedor
     * @return una lista de productos pertenecientes al proveedor
     */
    List<Producto> findByIdProveedor_Id(Long idProveedor);

}
