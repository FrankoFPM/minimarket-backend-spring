package org.minimarket.minimarketbackendspring.repositories;

import org.minimarket.minimarketbackendspring.entities.Distrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para manejar operaciones CRUD de la entidad Distrito.
 */
public interface DistritoRepository extends JpaRepository<Distrito, Long> {

    /**
     * Encuentra todos los distritos asociados a un departamento específico.
     *
     * @param idDepartamento el identificador del departamento
     * @return una lista de distritos pertenecientes al departamento
     */
    List<Distrito> findByIdDepartamento_Id(Long idDepartamento);
}