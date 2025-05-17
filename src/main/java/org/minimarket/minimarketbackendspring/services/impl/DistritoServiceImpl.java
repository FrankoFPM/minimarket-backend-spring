package org.minimarket.minimarketbackendspring.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.minimarket.minimarketbackendspring.dtos.DistritoDTO;
import org.minimarket.minimarketbackendspring.entities.Departamento;
import org.minimarket.minimarketbackendspring.entities.Distrito;
import org.minimarket.minimarketbackendspring.repositories.DepartamentoRepository;
import org.minimarket.minimarketbackendspring.repositories.DistritoRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.DistritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Distrito.
 */
@Service
public class DistritoServiceImpl implements DistritoService {

    @Autowired
    private DistritoRepository distritoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    /**
     * Obtiene una lista de todos los distritos.
     *
     * @return una lista de objetos de tipo DistritoDTO
     */
    @Override
    public List<DistritoDTO> findAll() {
        List<Distrito> distritos = distritoRepository.findAll();

        return distritos.stream()
                .map(d -> new DistritoDTO(
                        d.getId(),
                        d.getNombre(),
                        d.getDescripcion(),
                        d.getEstado(),
                        d.getIdDepartamento() != null ? d.getIdDepartamento().getId() : null,
                        d.getCreatedAt(),
                        d.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Busca un distrito por su ID.
     *
     * @param id el identificador del distrito
     * @return el distrito encontrado como DTO
     */
    @Override
    public DistritoDTO findById(Long id) {
        return distritoRepository.findById(id)
                .map(d -> new DistritoDTO(
                        d.getId(),
                        d.getNombre(),
                        d.getDescripcion(),
                        d.getEstado(),
                        d.getIdDepartamento() != null ? d.getIdDepartamento().getId() : null,
                        d.getCreatedAt(),
                        d.getUpdatedAt()
                ))
                .orElseThrow(() -> new EntityNotFoundException("Distrito no encontrado con ID: " + id));
    }

    /**
     * Obtiene una lista de distritos asociados a un departamento específico.
     *
     * @param idDepartamento el identificador del departamento
     * @return una lista de objetos DistritoDTO
     */
    @Override
    public List<DistritoDTO> findByDepartamentoId(Long idDepartamento) {
        return distritoRepository.findByIdDepartamento_Id(idDepartamento).stream()
                .map(d -> new DistritoDTO(
                        d.getId(),
                        d.getNombre(),
                        d.getDescripcion(),
                        d.getEstado(),
                        d.getIdDepartamento() != null ? d.getIdDepartamento().getId() : null,
                        d.getCreatedAt(),
                        d.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Guarda un nuevo distrito.
     *
     * @param distrito el objeto DistritoDTO a guardar
     */
    @Override
    public void save(DistritoDTO distrito) {
        Distrito d = new Distrito();
        d.setNombre(distrito.getNombre());
        d.setDescripcion(distrito.getDescripcion());
        d.setEstado(distrito.getEstado());

        // Verifica si el departamento existe antes de asignarlo
        Departamento departamento = departamentoRepository.findById(distrito.getIdDepartamento())
                .orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado con ID: " + distrito.getIdDepartamento()));
        d.setIdDepartamento(departamento);

        distritoRepository.save(d);
    }

    /**
     * Actualiza un distrito existente.
     *
     * @param distrito el objeto DistritoDTO a actualizar
     */
    @Override
    public void update(DistritoDTO distrito) {
        Distrito d = distritoRepository.findById(distrito.getId())
                .orElseThrow(() -> new EntityNotFoundException("Distrito no encontrado con ID: " + distrito.getId()));

        d.setNombre(distrito.getNombre());
        d.setDescripcion(distrito.getDescripcion());
        d.setEstado(distrito.getEstado());

        // Verifica si el departamento existe antes de asignarlo
        Departamento departamento = departamentoRepository.findById(distrito.getIdDepartamento())
                .orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado con ID: " + distrito.getIdDepartamento()));
        d.setIdDepartamento(departamento);

        distritoRepository.save(d);
    }

    /**
     * Elimina un distrito por su ID.
     *
     * @param id el identificador del distrito a eliminar
     */
    @Override
    public void delete(Long id) {
        if (!distritoRepository.existsById(id)) {
            throw new EntityNotFoundException("Distrito no encontrado con ID: " + id);
        }
        distritoRepository.deleteById(id);
    }
}