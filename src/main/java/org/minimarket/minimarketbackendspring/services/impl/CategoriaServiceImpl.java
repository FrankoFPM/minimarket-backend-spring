package org.minimarket.minimarketbackendspring.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.minimarket.minimarketbackendspring.dtos.CategoriaDTO;
import org.minimarket.minimarketbackendspring.entities.Categoria;
import org.minimarket.minimarketbackendspring.repositories.CategoriaRepository;
import org.minimarket.minimarketbackendspring.services.interfaces.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementación del servicio de Categoria.
 *
 * <p>
 * Aplica reglas de negocio y delega el acceso a datos al DAO.
 * </p>
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Obtiene una lista de todas las categorias.
     *
     * @return una lista de objetos de tipo Categoria
     */
    @Override
    public List<CategoriaDTO> findAll() {
        List<Categoria> categorias = categoriaRepository.findAll();

        return categorias.stream()
                .map(d -> new CategoriaDTO(
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
     * Busca una categoria por su ID.
     *
     * @param id el identificador de la categoria
     * @return la categoria encontrada o null si no existe
     */
    @Override
    public CategoriaDTO findById(Long id) {
        return categoriaRepository.findById(id)
                .map(d -> new CategoriaDTO(
                        d.getId(),
                        d.getNombre(),
                        d.getDescripcion(),
                        d.getEstado(),
                        d.getCreatedBy(),
                        d.getUpdateBy(),
                        d.getCreatedAt(),
                        d.getUpdatedAt()
                ))
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));
    }

    /**
     * Obtiene una lista de todas las categorias de un estado específico.
     *
     * @return una lista de objetos de tipo Categoria
     */
    @Override
    public List<CategoriaDTO> findByEstado(String estado) {
        return categoriaRepository.findByEstado(estado).stream()
                .map(cat -> new CategoriaDTO(
                        cat.getId(),
                        cat.getNombre(),
                        cat.getDescripcion(),
                        cat.getEstado(),
                        cat.getCreatedBy(),
                        cat.getUpdateBy(),
                        cat.getCreatedAt(),
                        cat.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    /**
     * Guarda una nueva categoria.
     *
     * @param categoria el objeto Categoria a guardar
     */
    @Override
    public void save(CategoriaDTO categoria) {
        Categoria cat = new Categoria();
        cat.setNombre(categoria.getNombre());
        cat.setDescripcion(categoria.getDescripcion());
        cat.setEstado(categoria.getEstado());
        cat.setCreatedBy(categoria.getCreatedBy());
        cat.setUpdateBy(categoria.getUpdatedBy());
        categoriaRepository.save(cat);
    }

    /**
     * Actualiza una categoria existente.
     *
     * @param categoria el objeto Categoria a actualizar
     */
    @Override
    public void update(CategoriaDTO categoria) {
        Categoria cat = categoriaRepository.findById(categoria.getId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + categoria.getId()));
        cat.setNombre(categoria.getNombre());
        cat.setDescripcion(categoria.getDescripcion());
        cat.setEstado(categoria.getEstado());
        //TODO: Validar el usuario que actualiza
        cat.setUpdateBy(categoria.getUpdatedBy());
        categoriaRepository.save(cat);
    }

    /**
     * Elimina una categoria por su ID.
     *
     * @param id el identificador de la categoria a eliminar
     */
    @Override
    public void delete(Long id) {
        Categoria cat = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));
        cat.setEstado("inactivo");
        categoriaRepository.save(cat);
    }
}
