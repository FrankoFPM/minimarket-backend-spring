package org.minimarket.minimarketbackendspring.entities;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "PROVEEDOR")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROVEEDOR_id_gen")
    @SequenceGenerator(name = "PROVEEDOR_id_gen", sequenceName = "proveedor_seq", allocationSize = 1)
    @Column(name = "ID_PROVEEDOR", nullable = false)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "CONTACTO", nullable = false, length = 100)
    private String contacto;

    @Column(name = "TELEFONO", nullable = false, length = 100)
    private String telefono;

    @Column(name = "DIRECCION", nullable = false, length = 100)
    private String direccion;

    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    @ColumnDefault("'Activo'")
    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;

    @Column(name = "CREATED_AT", updatable = false, insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(name = "UPDATED_AT", insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private Instant updatedAt;

    @Column(name = "CREATED_BY", length = 36)
    private String createdBy;

    @Column(name = "UPDATE_BY", length = 36)
    private String updateBy;

    @OneToMany(mappedBy = "idProveedor")
    @JsonManagedReference
    private Set<org.minimarket.minimarketbackendspring.entities.Producto> productos = new LinkedHashSet<>();

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Set<org.minimarket.minimarketbackendspring.entities.Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<org.minimarket.minimarketbackendspring.entities.Producto> productos) {
        this.productos = productos;
    }

}