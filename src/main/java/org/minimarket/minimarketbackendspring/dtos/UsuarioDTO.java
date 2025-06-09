package org.minimarket.minimarketbackendspring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioDTO {
    private String id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Long distritoId;
    private String distritoNombre;
    private String direccion;
    private String googleId;
    private String facebookId;
    private String rol;
    private String estado;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String createdBy;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String updatedBy;

    public UsuarioDTO(String id, String nombre, String apellido, String email, String telefono, String password, Long distritoId, String distritoNombre, String direccion, String googleId, String facebookId, String rol, String estado, String createdBy, String updatedBy) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.distritoId = distritoId;
        this.distritoNombre = distritoNombre;
        this.direccion = direccion;
        this.googleId = googleId;
        this.facebookId = facebookId;
        this.rol = rol;
        this.estado = estado;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public UsuarioDTO() {
        // Constructor por defecto
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDistritoId() {
        return distritoId;
    }

    public void setDistritoId(Long distritoId) {
        this.distritoId = distritoId;
    }

    public String getDistritoNombre() {
        return distritoNombre;
    }

    public void setDistritoNombre(String distritoNombre) {
        this.distritoNombre = distritoNombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
