package org.minimarket.minimarketbackendspring.repositories;

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

}
