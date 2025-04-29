package org.minimarket.minimarketbackendspring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "USUARIOS")
public class Usuario {
    @Id
    @SequenceGenerator(name = "USUARIOS_id_gen", sequenceName = "ISEQ$$_76643", allocationSize = 1)
    @Column(name = "ID_USUARIO", nullable = false, length = 36)
    private String idUsuario;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO", nullable = false, length = 50)
    private String apellido;

    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    @Column(name = "CLAVE")
    private String clave;

    @Column(name = "TELEFONO", length = 9)
    private String telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "ID_DISTRITO")
    @JsonBackReference
    private org.minimarket.minimarketbackendspring.entities.Distrito idDistrito;

    @Column(name = "DIRECCION", length = 100)
    private String direccion;

    @Column(name = "GOOGLE_ID", length = 50)
    private String googleId;

    @Column(name = "FACEBOOK_ID", length = 50)
    private String facebookId;

    @ColumnDefault("'cliente'")
    @Column(name = "ROL", nullable = false, length = 20)
    private String rol;

    @ColumnDefault("'Activo'")
    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATED_AT")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "UPDATED_AT")
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "CREATED_BY")
    private org.minimarket.minimarketbackendspring.entities.Usuario createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "UPDATE_BY")
    private org.minimarket.minimarketbackendspring.entities.Usuario updateBy;

    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    private Set<org.minimarket.minimarketbackendspring.entities.Departamento> departamentos_c_by = new LinkedHashSet<>();

    @OneToMany(mappedBy = "updateBy")
    @JsonIgnore
    private Set<org.minimarket.minimarketbackendspring.entities.Departamento> departamentos_u_by = new LinkedHashSet<>();

    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    private Set<org.minimarket.minimarketbackendspring.entities.Distrito> distritos_c_by = new LinkedHashSet<>();

    @OneToMany(mappedBy = "updateBy")
    @JsonIgnore
    private Set<org.minimarket.minimarketbackendspring.entities.Distrito> distritos_u_by = new LinkedHashSet<>();

    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    private Set<org.minimarket.minimarketbackendspring.entities.Usuario> usuarios_c_by = new LinkedHashSet<>();

    @OneToMany(mappedBy = "updateBy")
    @JsonIgnore
    private Set<org.minimarket.minimarketbackendspring.entities.Usuario> usuarios_u_by = new LinkedHashSet<>();

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public org.minimarket.minimarketbackendspring.entities.Distrito getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(org.minimarket.minimarketbackendspring.entities.Distrito idDistrito) {
        this.idDistrito = idDistrito;
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

    public org.minimarket.minimarketbackendspring.entities.Usuario getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(org.minimarket.minimarketbackendspring.entities.Usuario createdBy) {
        this.createdBy = createdBy;
    }

    public org.minimarket.minimarketbackendspring.entities.Usuario getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(org.minimarket.minimarketbackendspring.entities.Usuario updateBy) {
        this.updateBy = updateBy;
    }

    public Set<org.minimarket.minimarketbackendspring.entities.Departamento> getDepartamentos_c_by() {
        return departamentos_c_by;
    }

    public void setDepartamentos_c_by(Set<org.minimarket.minimarketbackendspring.entities.Departamento> departamentos_c_by) {
        this.departamentos_c_by = departamentos_c_by;
    }

    public Set<org.minimarket.minimarketbackendspring.entities.Departamento> getDepartamentos_u_by() {
        return departamentos_u_by;
    }

    public void setDepartamentos_u_by(Set<org.minimarket.minimarketbackendspring.entities.Departamento> departamentos_u_by) {
        this.departamentos_u_by = departamentos_u_by;
    }

    public Set<org.minimarket.minimarketbackendspring.entities.Distrito> getDistritos_c_by() {
        return distritos_c_by;
    }

    public void setDistritos_c_by(Set<org.minimarket.minimarketbackendspring.entities.Distrito> distritos_c_by) {
        this.distritos_c_by = distritos_c_by;
    }

    public Set<org.minimarket.minimarketbackendspring.entities.Distrito> getDistritos_u_by() {
        return distritos_u_by;
    }

    public void setDistritos_u_by(Set<org.minimarket.minimarketbackendspring.entities.Distrito> distritos_u_by) {
        this.distritos_u_by = distritos_u_by;
    }

    public Set<org.minimarket.minimarketbackendspring.entities.Usuario> getUsuarios_c_by() {
        return usuarios_c_by;
    }

    public void setUsuarios_c_by(Set<org.minimarket.minimarketbackendspring.entities.Usuario> usuarios_c_by) {
        this.usuarios_c_by = usuarios_c_by;
    }

    public Set<org.minimarket.minimarketbackendspring.entities.Usuario> getUsuarios_u_by() {
        return usuarios_u_by;
    }

    public void setUsuarios_u_by(Set<org.minimarket.minimarketbackendspring.entities.Usuario> usuarios_u_by) {
        this.usuarios_u_by = usuarios_u_by;
    }

}