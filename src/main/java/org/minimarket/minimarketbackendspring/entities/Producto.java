package org.minimarket.minimarketbackendspring.entities;

import java.time.Instant;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCTO")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTO_id_gen")
    @SequenceGenerator(name = "PRODUCTO_id_gen", sequenceName = "ISEQ$$_76643", allocationSize = 1)
    @Column(name = "ID_PRODUCTO", nullable = false)
    private String id;

    @Column(name = "PRODUCTO", nullable = false, length = 100)
    private String nombre;

    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "PRECIO", nullable = false, length = 100)
    private String precio;

    @Column(name = "STOCK", nullable = false, length = 100)
    private String stock;

    @Column(name = "FOTO", nullable = false, length = 100)
    private String foto;

    @ColumnDefault("'Activo'")
    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATED_AT")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "UPDATED_AT")
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_CATEGORIA", nullable = false)
    @JsonBackReference
    private org.minimarket.minimarketbackendspring.entities.Categoria idCategoria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_PROVEEDOR", nullable = false)
    @JsonBackReference
    private org.minimarket.minimarketbackendspring.entities.Proveedor idProveedor;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public org.minimarket.minimarketbackendspring.entities.Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(org.minimarket.minimarketbackendspring.entities.Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public org.minimarket.minimarketbackendspring.entities.Proveedor getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(org.minimarket.minimarketbackendspring.entities.Proveedor idProveedor) {
        this.idProveedor = idProveedor;
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

}