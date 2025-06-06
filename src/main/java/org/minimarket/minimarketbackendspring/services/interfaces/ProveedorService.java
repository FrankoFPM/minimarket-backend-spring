package org.minimarket.minimarketbackendspring.services.interfaces;

import java.util.List;
import java.util.Optional;

import org.minimarket.minimarketbackendspring.dtos.ProveedorDTO;

/**
 * Interfaz para definir las operaciones relacionadas con la entidad Proveedor.
 */
public interface ProveedorService {

    /**
     * Obtiene una lista de todos los proveedores.
     *
     * @return
     */
    List<ProveedorDTO> findAll();

    /**
     * Busca un proveedor por su ID.
     *
     * @param id el identificador del proveedor
     * @return
     */
    ProveedorDTO findById(String id);

    /**
     * Busca un proveedor por su nombre.
     *
     * @param nombre el nombre del proveedor
     * @return
     */
    Optional<ProveedorDTO> findByNombre(String nombre);

    /**
     * Busca un proveedor por su email.
     *
     * @param email el email del proveedor
     * @return
     */
    Optional<ProveedorDTO> findByEmail(String email);

    /**
     * Guarda un nuevo proveedor.
     *
     * @param proveedor
     */
    void save(ProveedorDTO proveedor);

    /**
     * Actualiza un proveedor existente.
     *
     * @param proveedor
     */
    void update(ProveedorDTO proveedor);

    /**
     * Elimina un proveedor por su ID.
     *
     * @param id
     */
    void delete(String id);
}
