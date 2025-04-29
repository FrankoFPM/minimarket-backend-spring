package org.minimarket.minimarketbackendspring.services.interfaces;

import org.minimarket.minimarketbackendspring.entities.Departamento;

import java.util.List;

/**
 * Interfaz para definir las operaciones relacionadas con la entidad Departamento.
 *
 * <p><strong>IMPORTANTE:</strong> Esta interfaz debe respetar el principio de responsabilidad única (SRP)
 * del diseño SOLID. Las implementaciones deben centrarse exclusivamente en la lógica de negocio
 * y delegar las operaciones de acceso a datos al DAO correspondiente.</p>
 */
public interface DepartamentoService {

    /**
     * Obtiene una lista de todos los departamentos.
     *
     * @return una lista de objetos de tipo Departamento
     */
    List<Departamento> findAll();

    /**
     * Busca un departamento por su ID.
     *
     * @param id el identificador del departamento
     * @return el departamento encontrado o null si no existe
     */
    Departamento findById(Long id);

    /**
     * Guarda un nuevo departamento.
     *
     * @param departamento el objeto Departamento a guardar
     */
    void save(Departamento departamento);

    /**
     * Actualiza un departamento existente.
     *
     * @param departamento el objeto Departamento a actualizar
     */
    void update(Departamento departamento);

    /**
     * Elimina un departamento por su ID.
     *
     * @param id el identificador del departamento a eliminar
     */
    void delete(Long id);
}
