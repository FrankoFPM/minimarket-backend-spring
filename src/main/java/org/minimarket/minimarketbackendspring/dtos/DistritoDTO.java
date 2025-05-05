package org.minimarket.minimarketbackendspring.dtos;

import java.time.Instant;

/**
 * DTO para la entidad Distrito.
 */
public class DistritoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;
    private Long idDepartamento;
    private Instant createdAt;
    private Instant updatedAt;

    // Constructor vacío
    public DistritoDTO() {
    }

    // Constructor con todos los campos
    public DistritoDTO(Long id, String nombre, String descripcion, String estado, Long idDepartamento, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.idDepartamento = idDepartamento;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Long idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}