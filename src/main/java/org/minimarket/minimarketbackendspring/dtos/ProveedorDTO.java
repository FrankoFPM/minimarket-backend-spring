package org.minimarket.minimarketbackendspring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.OffsetDateTime;

/**
 * DTO para la entidad Proveedor.
 */
public class ProveedorDTO {
    private final Long id;
    private final String nombre;
    private final String contacto;
    private final String telefono;
    private final String direccion;
    private final String email;
    private final String estado;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;

    public ProveedorDTO(Long id, String nombre, String contacto, String telefono, String direccion, String email, String estado, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.nombre = nombre;
        this.contacto = contacto;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public String getEstado() {
        return estado;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombre = " + nombre + ", " +
                "contacto = " + contacto + ", " +
                "telefono = " + telefono + ", " +
                "direccion = " + direccion + ", " +
                "email = " + email + ", " +
                "estado = " + estado + ", " +
                "createdAt = " + createdAt + ", " +
                "updatedAt = " + updatedAt + ", ";
    }
    

}