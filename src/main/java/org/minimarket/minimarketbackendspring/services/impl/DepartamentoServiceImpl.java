package org.minimarket.minimarketbackendspring.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.minimarket.minimarketbackendspring.daos.interfaces.DepartamentoDAO;
import org.minimarket.minimarketbackendspring.entities.Departamento;
import org.minimarket.minimarketbackendspring.services.interfaces.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de Departamento.
 *
 * <p>Aplica reglas de negocio y delega el acceso a datos al DAO.</p>
 */
@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DepartamentoDAO departamentoDAO;


    /**
     * Obtiene una lista de todos los departamentos.
     *
     * @return una lista de objetos de tipo Departamento
     */
    @Override
    public List<Departamento> findAll() {
        return departamentoDAO.findAll();
    }

    /**
     * Busca un departamento por su ID.
     *
     * @param id el identificador del departamento
     * @return el departamento encontrado o null si no existe
     */
    @Override
    public Departamento findById(Long id) {
        Departamento departamento = departamentoDAO.findById(id);
        if (departamento == null) {
            throw new EntityNotFoundException("Departamento no encontrado con ID: " + id);
        }
        return departamento;
    }

    /**
     * Guarda un nuevo departamento.
     *
     * @param departamento el objeto Departamento a guardar
     */
    @Override
    public void save(Departamento departamento) {
        departamentoDAO.save(departamento);
    }

    /**
     * Actualiza un departamento existente.
     *
     * @param departamento el objeto Departamento a actualizar
     */
    @Override
    public void update(Departamento departamento) {
        // Validamos antes de actualizar
        Departamento existing = departamentoDAO.findById(departamento.getId());
        if (existing == null) {
            throw new EntityNotFoundException("Departamento no encontrado con ID: " + departamento.getId());
        }
        departamentoDAO.update(departamento);
    }

    /**
     * Elimina un departamento por su ID.
     *
     * @param id el identificador del departamento a eliminar
     */
    @Override
    public void delete(Long id) {
        Departamento departamento = departamentoDAO.findById(id);
        if (departamento == null) {
            throw new EntityNotFoundException("Departamento no encontrado con ID: " + id);
        }
        departamentoDAO.delete(departamento);
    }
}
