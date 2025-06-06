package org.minimarket.minimarketbackendspring.dtos;

/**
 * DTO para la entidad Producto.
 */
public class ProductoDTO {
    private String idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private Long stock;
    private String foto;
    private String estado;
    private Long idCategoria;
    private String categoriaNombre;
    private Long idProveedor;
    private String proveedorNombre;
    private String createdAt;
    private String updatedAt;

    // Constructor vacío
    public ProductoDTO() {
    }

    // Constructor con todos los campos
    public ProductoDTO(String idProducto, String nombre, String descripcion, double precio, Long stock, String foto,
            String estado, Long idCategoria, String categoriaNombre, Long idProveedor, String proveedorNombre,
            String createdAt, String updatedAt) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.foto = foto;
        this.estado = estado;
        this.idCategoria = idCategoria;
        this.categoriaNombre = categoriaNombre;
        this.idProveedor = idProveedor;
        this.proveedorNombre = proveedorNombre;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
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
    

}