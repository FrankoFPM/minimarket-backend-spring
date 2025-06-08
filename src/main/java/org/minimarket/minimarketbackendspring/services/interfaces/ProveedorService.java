package org.minimarket.minimarketbackendspring.services.interfaces;

import java.util.List;

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
    ProveedorDTO findById(Long id);

    /**
     * Busca un proveedor por su nombre.
     *
     * @param nombre el nombre del proveedor
     * @return
     */
    ProveedorDTO findByNombre(String nombre);

    /**
     * Busca un proveedor por su email.
     *
     * @param email el email del proveedor
     * @return
     */
    ProveedorDTO findByEmail(String email);

    /**
     * Obtiene una lista de todos los proveedores activos.
     *
     * @return una lista de objetos de tipo Proveedor
     */
    List<ProveedorDTO> findByEstado(String estado);

    /**
     * Guarda un nuevo proveedor en la base de datos.
     *
     * @param proveedor el objeto ProveedorDTO que contiene los datos del proveedor a guardar
     * @throws IllegalArgumentException si los datos del proveedor no son válidos
     */
    void save(ProveedorDTO proveedor);

    /**
     * Actualiza un proveedor existente en la base de datos.
     *
     * @param proveedor el objeto ProveedorDTO que contiene los datos actualizados del proveedor
     * @throws jakarta.persistence.EntityNotFoundException si el proveedor con el ID especificado no existe
     */
    void update(ProveedorDTO proveedor);

    /**
     * Elimina un proveedor por su ID.
     *
     * @param id el identificador del proveedor a eliminar
     */
    void delete(Long id);
}
