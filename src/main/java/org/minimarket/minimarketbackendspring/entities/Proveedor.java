package org.minimarket.minimarketbackendspring.entities;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "PROVEEDOR")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROVEEDOR", nullable = false)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "CONTACTO", nullable = false, length = 100)
    private String contacto;

    @Column(name = "TELEFONO", length = 15)
    private String telefono;

    @Column(name = "DIRECCION", length = 100)
    private String direccion;

    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    @ColumnDefault("'Activo'")
    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private OffsetDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "CREATED_BY")
    private Usuario createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "UPDATE_BY")
    private Usuario updateBy;

    @OneToMany(mappedBy = "idProveedor")
    private Set<Producto> productos = new LinkedHashSet<>();

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

    public Usuario getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Usuario createdBy) {
        this.createdBy = createdBy;
    }

    public Usuario getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Usuario updateBy) {
        this.updateBy = updateBy;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

}