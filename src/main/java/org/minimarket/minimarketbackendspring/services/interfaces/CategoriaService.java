package org.minimarket.minimarketbackendspring.services.interfaces;

import java.util.List;

import org.minimarket.minimarketbackendspring.dtos.CategoriaDTO;

/**
 * Interfaz para definir las operaciones relacionadas con la entidad Categoria.
 *
 * <p><strong>IMPORTANTE:</strong> Esta interfaz debe respetar el principio de responsabilidad única (SRP)
 * del diseño SOLID. Las implementaciones deben centrarse exclusivamente en la lógica de negocio
 * y delegar las operaciones de acceso a datos al DAO correspondiente.</p>
 */
public interface CategoriaService {

    /**
     * Obtiene una lista de todos las categorías.
     *
     * @return una lista de objetos de tipo Categoría
     */
    List<CategoriaDTO> findAll();

    /**
     * Busca una categoría por su ID.
     *
     * @param id el identificador de la categoría
     * @return la categoría encontrada o null si no existe
     */
    CategoriaDTO findById(Long id);

    /**
     * Obtiene una lista de todos las categorías activas.
     *
     * @return una lista de objetos de tipo Departamento
     */
    List<CategoriaDTO> findByEstado(String estado);

    /**
     * Guarda una nueva categoría en la base de datos.
     *
     * @param categoria el objeto CategoriaDTO que contiene los datos de la categoria a guardar
     * @throws IllegalArgumentException si los datos de la categoria no son válidos
     */
    void save(CategoriaDTO categoria);

    /**
     * Actualiza una categoría existente en la base de datos.
     *
     * @param categoria el objeto CategoriaDTO que contiene los datos actualizados de la categoría
     * @throws jakarta.persistence.EntityNotFoundException si la categoría con el ID especificado no existe
     */
    void update(CategoriaDTO categoria);

    /**
     * Elimina una categoría por su ID.
     *
     * @param id el identificador de la categoría a eliminar
     */
    void delete(Long id);
}
