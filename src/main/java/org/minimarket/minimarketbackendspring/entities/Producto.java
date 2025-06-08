package org.minimarket.minimarketbackendspring.entities;

import java.time.OffsetDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "PRODUCTOS")
public class Producto {
    @Id
    @ColumnDefault("RAWTOHEX(SYS_GUID())")
    @Column(name = "ID_PRODUCTO", nullable = false, length = 36)
    private String idProducto;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "PRECIO", nullable = false)
    private Double precio;

    @Column(name = "STOCK", nullable = false)
    private Long stock;

    @Column(name = "FOTO", length = 100)
    private String foto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "ID_CATEGORIA", nullable = false)
    private Categoria idCategoria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "ID_PROVEEDOR", nullable = false)
    private Proveedor idProveedor;

    @ColumnDefault("'activo'")
    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
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

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Proveedor getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Proveedor idProveedor) {
        this.idProveedor = idProveedor;
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

}