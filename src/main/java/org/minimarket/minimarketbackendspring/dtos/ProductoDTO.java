package org.minimarket.minimarketbackendspring.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO para la entidad Producto.
 */
public class ProductoDTO {
    private String idProducto;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Long stock;
    private String foto;
    private String estado;
    private String categoriaNombre;   // Solo el nombre de la categoría
    private String proveedorNombre;   // Solo el nombre del proveedor
    private String createdAt;
    private String updatedAt;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String createdBy;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String updateBy;

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long idCategoria;      // <--- ID para inserción
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long idProveedor;      // <--- ID para inserción

    // Constructor vacío
    public ProductoDTO() {
    }

    // Constructor con todos los campos


    public ProductoDTO(String idProducto, String nombre, String descripcion, Double precio, Long stock, String foto, String estado, String categoriaNombre, String proveedorNombre, String createdAt, String updatedAt, String createdBy, String updateBy, Long idCategoria, Long idProveedor) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.foto = foto;
        this.estado = estado;
        this.categoriaNombre = categoriaNombre;
        this.proveedorNombre = proveedorNombre;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updateBy = updateBy;
        this.idCategoria = idCategoria;
        this.idProveedor = idProveedor;
    }

    // Getters y Setters
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
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

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }
}