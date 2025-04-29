package org.minimarket.minimarketbackendspring.daos.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.minimarket.minimarketbackendspring.daos.interfaces.DepartamentoDAO;
import org.minimarket.minimarketbackendspring.entities.Departamento;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementación de la interfaz DepartamentoDAO utilizando JPA EntityManager.
 *
 * <p>
 * <strong>IMPORTANTE:</strong> Esta clase solo debe realizar operaciones de
 * acceso
 * a datos (persistencia, consulta, eliminación). Todas las validaciones de
 * negocio
 * deben ser gestionadas en la capa de servicios (Service) para respetar el
 * principio
 * de responsabilidad única (SRP) de SOLID.
 * </p>
 */
@Repository
public class DepartamentoDaoImpl implements DepartamentoDAO {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Obtiene una lista de todos los departamentos.
     *
     * @return una lista de objetos de tipo Departamento
     */
    @Override
    public List<Departamento> findAll() {
        return entityManager.createQuery("SELECT d FROM Departamento d", Departamento.class).getResultList();
    }

    /**
     * Busca un departamento por su ID.
     *
     * @param id el identificador del departamento
     * @return el departamento encontrado o null si no existe
     */
    @Override
    public Departamento findById(Long id) {
        return entityManager.find(Departamento.class, id);
    }

    /**
     *
     * Guarda un nuevo departamento o actualiza uno existente.
     *
     * @param departamento el objeto Departamento a guardar o actualizar
     */
    @Override
    public void save(Departamento departamento) {
        entityManager.merge(departamento);
    }

    /**
     *
     * Actualiza un departamento existente.
     *
     * @param departamento el objeto Departamento a actualizar
     */
    @Override
    public void update(Departamento departamento) {
        entityManager.merge(departamento);
    }

    /**
     *
     * Elimina un departamento.
     *
     * @param departamento el objeto Departamento a eliminar
     */
    @Override
    public void delete(Departamento departamento) {
        entityManager.remove(departamento);
    }
}
