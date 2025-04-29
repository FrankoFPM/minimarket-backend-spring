package org.minimarket.minimarketbackendspring.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.minimarket.minimarketbackendspring.dtos.DepartamentoDTO;
import org.minimarket.minimarketbackendspring.entities.Departamento;
import org.minimarket.minimarketbackendspring.repositories.DepartamentoRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Departamento.
 *
 * <p>
 * Aplica reglas de negocio y delega el acceso a datos al DAO.
 * </p>
 */
@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    /**
     * Obtiene una lista de todos los departamentos.
     *
     * @return una lista de objetos de tipo Departamento
     */
    @Override
    public List<DepartamentoDTO> findAll() {
        List<Departamento> departamentos = departamentoRepository.findAll();

        return departamentos.stream()
                .map(d -> new DepartamentoDTO(
                        d.getId(),
                        d.getNombre(),
                        d.getDescripcion(),
                        d.getEstado(),
                        d.getCreatedBy(),
                        d.getUpdateBy(),
                        d.getCreatedAt(),
                        d.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Busca un departamento por su ID.
     *
     * @param id el identificador del departamento
     * @return el departamento encontrado o null si no existe
     */
    @Override
    public DepartamentoDTO findById(Long id) {
        return departamentoRepository.findById(id)
                .map(d -> new DepartamentoDTO(
                        d.getId(),
                        d.getNombre(),
                        d.getDescripcion(),
                        d.getEstado(),
                        d.getCreatedBy(),
                        d.getUpdateBy(),
                        d.getCreatedAt(),
                        d.getUpdatedAt()
                ))
                .orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado con ID: " + id));
    }

    /**
     * Obtiene una lista de todos los departamentos de un estado específico.
     *
     * @return una lista de objetos de tipo Departamento
     */
    @Override
    public List<DepartamentoDTO> findByEstado(String estado) {
        return departamentoRepository.findByEstado(estado).stream()
                .map(d -> new DepartamentoDTO(
                        d.getId(),
                        d.getNombre(),
                        d.getDescripcion(),
                        d.getEstado(),
                        d.getCreatedBy(),
                        d.getUpdateBy(),
                        d.getCreatedAt(),
                        d.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    /**
     * Guarda un nuevo departamento.
     *
     * @param departamento el objeto Departamento a guardar
     */
    @Override
    public void save(DepartamentoDTO departamento) {
        Departamento d = new Departamento();
        d.setNombre(departamento.getNombre());
        d.setDescripcion(departamento.getDescripcion());
        d.setEstado(departamento.getEstado());
        d.setCreatedBy(departamento.getCreatedBy());
        d.setUpdateBy(departamento.getUpdatedBy());
        departamentoRepository.save(d);
    }

    /**
     * Actualiza un departamento existente.
     *
     * @param departamento el objeto Departamento a actualizar
     */
    @Override
    public void update(DepartamentoDTO departamento) {
        Departamento dep = departamentoRepository.findById(departamento.getId())
                .orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado con ID: " + departamento.getId()));
        dep.setNombre(departamento.getNombre());
        dep.setDescripcion(departamento.getDescripcion());
        dep.setEstado(departamento.getEstado());
        //TODO: Validar el usuario que actualiza
        dep.setUpdateBy(departamento.getUpdatedBy());
        departamentoRepository.save(dep);
    }

    /**
     * Elimina un departamento por su ID.
     *
     * @param id el identificador del departamento a eliminar
     */
    @Override
    public void delete(Long id) {
        if (!departamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Departamento no encontrado con ID: " + id);
        }
        departamentoRepository.deleteById(id);
    }
}
