package org.minimarket.minimarketbackendspring.services.interfaces;

import org.minimarket.minimarketbackendspring.dtos.DepartamentoDTO;

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
    List<DepartamentoDTO> findAll();

    /**
     * Busca un departamento por su ID.
     *
     * @param id el identificador del departamento
     * @return el departamento encontrado o null si no existe
     */
    DepartamentoDTO  findById(Long id);

    /**
     * Obtiene una lista de todos los departamentos activos.
     *
     * @return una lista de objetos de tipo Departamento
     */
    List<DepartamentoDTO> findByEstado(String estado);

    /**
     * Guarda un nuevo departamento en la base de datos.
     *
     * @param departamento el objeto DepartamentoDTO que contiene los datos del departamento a guardar
     * @throws IllegalArgumentException si los datos del departamento no son válidos
     */
    void save(DepartamentoDTO departamento);

    /**
     * Actualiza un departamento existente en la base de datos.
     *
     * @param departamento el objeto DepartamentoDTO que contiene los datos actualizados del departamento
     * @throws jakarta.persistence.EntityNotFoundException si el departamento con el ID especificado no existe
     */
    void update(DepartamentoDTO departamento);

    /**
     * Elimina un departamento por su ID.
     *
     * @param id el identificador del departamento a eliminar
     */
    void delete(Long id);
}
