package org.minimarket.minimarketbackendspring.daos.interfaces;

import org.minimarket.minimarketbackendspring.entities.Departamento;

import java.util.List;

/**
 * Interfaz para el acceso a datos de la entidad Departamento.
 *
 * <p><strong>IMPORTANTE:</strong> Este DAO debe usarse exclusivamente para operaciones
 * de acceso a la base de datos. Las validaciones de negocio deben realizarse en la
 * capa de servicios (Service) para respetar el principio de responsabilidad única (SRP).</p>
 */
public interface DepartamentoDAO {
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
     * Guarda un nuevo departamento o actualiza uno existente.
     *
     * @param departamento el objeto Departamento a guardar o actualizar
     */
    void save(Departamento departamento);

    /**
     * Actualiza un departamento existente.
     *
     * @param departamento el objeto Departamento a actualizar
     */
    void update(Departamento departamento);

    /**
     * Elimina un departamento.
     *
     * @param departamento el objeto Departamento a eliminar
     */
    void delete(Departamento departamento);
}
