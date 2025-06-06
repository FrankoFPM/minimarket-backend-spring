package org.minimarket.minimarketbackendspring.entities;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCTOS")
public class Producto {
    @Id
    @SequenceGenerator(name = "PRODUCTOS_id_gen", sequenceName = "ISEQ$$_76643", allocationSize = 1)
    @Column(name = "ID_PRODUCTO", nullable = false, length = 36)
    private String idProducto;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "PRECIO", nullable = false)
    private double precio;

    @Column(name = "STOCK", nullable = false)
    private Long stock;

    @Column(name = "FOTO")
    private String foto;

    @ColumnDefault("'Activo'")
    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_CATEGORIA")
    @JsonBackReference
    private org.minimarket.minimarketbackendspring.entities.Categoria idCategoria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_PROVEEDOR")
    @JsonBackReference
    private org.minimarket.minimarketbackendspring.entities.Proveedor idProveedor;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATED_AT")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "UPDATED_AT")
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "CREATED_BY")
    private org.minimarket.minimarketbackendspring.entities.Producto createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "UPDATE_BY")
    private org.minimarket.minimarketbackendspring.entities.Producto updateBy;

    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    private Set<org.minimarket.minimarketbackendspring.entities.Producto> productos_c_by = new LinkedHashSet<>();

    @OneToMany(mappedBy = "updateBy")
    @JsonIgnore
    private Set<org.minimarket.minimarketbackendspring.entities.Producto> productos_u_by = new LinkedHashSet<>();

    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    private Set<org.minimarket.minimarketbackendspring.entities.Categoria> categorias_c_by = new LinkedHashSet<>();

    @OneToMany(mappedBy = "updateBy")
    @JsonIgnore
    private Set<org.minimarket.minimarketbackendspring.entities.Categoria> categorias_u_by = new LinkedHashSet<>();

    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    private Set<org.minimarket.minimarketbackendspring.entities.Proveedor> proveedores_c_by = new LinkedHashSet<>();

    @OneToMany(mappedBy = "updateBy")
    @JsonIgnore
    private Set<org.minimarket.minimarketbackendspring.entities.Proveedor> proveedores_u_by = new LinkedHashSet<>();

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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public org.minimarket.minimarketbackendspring.entities.Producto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(org.minimarket.minimarketbackendspring.entities.Producto createdBy) {
        this.createdBy = createdBy;
    }

    public org.minimarket.minimarketbackendspring.entities.Producto getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(org.minimarket.minimarketbackendspring.entities.Producto updateBy) {
        this.updateBy = updateBy;
    }

    public Set<org.minimarket.minimarketbackendspring.entities.Producto> getProductos_c_by() {
        return productos_c_by;
    }

    public void setProductos_c_by(Set<org.minimarket.minimarketbackendspring.entities.Producto> productos_c_by) {
        this.productos_c_by = productos_c_by;
    }

    public Set<org.minimarket.minimarketbackendspring.entities.Producto> getProductos_u_by() {
        return productos_u_by;
    }

    public void setProductos_u_by(Set<org.minimarket.minimarketbackendspring.entities.Producto> productos_u_by) {
        this.productos_u_by = productos_u_by;
    }

    public Set<org.minimarket.minimarketbackendspring.entities.Categoria> getCategorias_c_by() {
        return categorias_c_by;
    }

    public void setCategorias_c_by(Set<org.minimarket.minimarketbackendspring.entities.Categoria> categorias_c_by) {
        this.categorias_c_by = categorias_c_by;
    }

    public Set<org.minimarket.minimarketbackendspring.entities.Categoria> getCategorias_u_by() {
        return categorias_u_by;
    }

    public void setCategorias_u_by(Set<org.minimarket.minimarketbackendspring.entities.Categoria> categorias_u_by) {
        this.categorias_u_by = categorias_u_by;
    }

    public Set<org.minimarket.minimarketbackendspring.entities.Proveedor> getProveedores_c_by() {
        return proveedores_c_by;
    }

    public void setProveedores_c_by(Set<org.minimarket.minimarketbackendspring.entities.Proveedor> proveedores_c_by) {
        this.proveedores_c_by = proveedores_c_by;
    }

    public Set<org.minimarket.minimarketbackendspring.entities.Proveedor> getProveedores_u_by() {
        return proveedores_u_by;
    }

    public void setProveedores_u_by(Set<org.minimarket.minimarketbackendspring.entities.Proveedor> proveedores_u_by) {
        this.proveedores_u_by = proveedores_u_by;
    }

       

}