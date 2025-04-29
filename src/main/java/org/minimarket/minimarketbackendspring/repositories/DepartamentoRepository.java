package org.minimarket.minimarketbackendspring.repositories;

import org.minimarket.minimarketbackendspring.entities.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    // Colocar aquí los métodos personalizados que necesites para la entidad Departamento
    // Por ejemplo, si necesitas un método para buscar departamentos por nombre:
    // List<Departamento> findByNombre(String nombre);
}
