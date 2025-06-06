package org.minimarket.minimarketbackendspring.repositories;

import java.util.List;
import java.util.Optional;

import org.minimarket.minimarketbackendspring.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para manejar operaciones CRUD de la entidad Proveedor.
 */
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    /**
     * Encuentra un proveedor por su id.
     *
     * @param id identificador del proveedor
     * @return
     */
    Optional<Proveedor> findById(Long id);
    
    /**
     * Encuentra un proveedor por su nombre.
     *
     * @param nombre nombre del proveedor
     * @return
     */
    Optional<Proveedor> findByNombre(String nombre);

    /**
     * Encuentra un proveedor por su dirección de email.
     *
     * @param email dirección de email del proveedor
     * @return
     */
    Optional<Proveedor> findByEmail(String email);

    List<Proveedor> findByEstado(String estado); // Método para buscar proveedores por estado

}
