package org.minimarket.minimarketbackendspring.repositories;

import java.util.List;
import java.util.Optional;

import org.minimarket.minimarketbackendspring.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Colocar aquí los métodos personalizados que necesites para la entidad
    // Categoría
    // Por ejemplo, si necesitas un método para buscar categorías por nombre:
    // List<Categoria> findByNombre(String nombre);

    List<Categoria> findByEstado(String estado); // Método para buscar categorías por estado

    

    /**
     * Encuentra una categoría por su id.
     *
     * @param id identificador de la categoría
     * @return
     */
    Optional<Categoria> findById(Long id);
}
