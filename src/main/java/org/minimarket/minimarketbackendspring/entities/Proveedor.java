package org.minimarket.minimarketbackendspring.entities;

import java.time.Instant;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "PROVEEDOR")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROVEEDOR_id_gen")
    @SequenceGenerator(name = "PROVEEDOR_id_gen", sequenceName = "ISEQ$$_76643", allocationSize = 1)
    @Column(name = "ID_PROVEEDOR", nullable = false)
    private String idProveedor;

    @Column(name = "PROVEEDOR", nullable = false, length = 100)
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

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
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

}