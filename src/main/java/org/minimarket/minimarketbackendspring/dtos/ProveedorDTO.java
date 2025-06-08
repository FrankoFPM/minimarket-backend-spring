package org.minimarket.minimarketbackendspring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.OffsetDateTime;

/**
 * DTO para la entidad Proveedor.
 */
public class ProveedorDTO {
    private Long id;
    private String nombre;
    private String contacto;
    private String telefono;
    private String direccion;
    private String email;
    private String estado;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

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

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
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