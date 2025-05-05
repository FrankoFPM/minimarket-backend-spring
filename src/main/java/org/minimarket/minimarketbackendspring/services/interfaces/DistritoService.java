package org.minimarket.minimarketbackendspring.services.interfaces;

import org.minimarket.minimarketbackendspring.dtos.DistritoDTO;

import java.util.List;

/**
 * Interfaz para definir las operaciones relacionadas con la entidad Distrito.
 */
public interface DistritoService {

    /**
     * Obtiene una lista de todos los distritos.
     *
     * @return una lista de objetos DistritoDTO
     */
    List<DistritoDTO> findAll();

    /**
     * Busca un distrito por su ID.
     *
     * @param id el identificador del distrito
     * @return el distrito encontrado como DTO
     */
    DistritoDTO findById(Long id);

    /**
     * Obtiene una lista de distritos asociados a un departamento específico.
     *
     * @param idDepartamento el identificador del departamento
     * @return una lista de objetos DistritoDTO
     */
    List<DistritoDTO> findByDepartamentoId(Long idDepartamento);

    /**
     * Guarda un nuevo distrito.
     *
     * @param distrito el objeto DistritoDTO a guardar
     */
    void save(DistritoDTO distrito);

    /**
     * Actualiza un distrito existente.
     *
     * @param distrito el objeto DistritoDTO a actualizar
     */
    void update(DistritoDTO distrito);

    /**
     * Elimina un distrito por su ID.
     *
     * @param id el identificador del distrito a eliminar
     */
    void delete(Long id);
}