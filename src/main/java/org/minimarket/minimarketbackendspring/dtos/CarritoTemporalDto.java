package org.minimarket.minimarketbackendspring.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.minimarket.minimarketbackendspring.entities.CarritoTemporal}
 */
public class CarritoTemporalDto implements Serializable {
    private Long id;
    private String idUsuario;
    private String idProducto;
    private String idProductoNombre;
    private Double idProductoPrecio;
    private Long cantidad;
    private LocalDateTime fechaAgregado;

    public CarritoTemporalDto() {
    }

    public CarritoTemporalDto(Long id, String idUsuario, String idProducto, String idProductoNombre, Double idProductoPrecio, Long cantidad, LocalDateTime fechaAgregado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idProducto = idProducto;
        this.idProductoNombre = idProductoNombre;
        this.idProductoPrecio = idProductoPrecio;
        this.cantidad = cantidad;
        this.fechaAgregado = fechaAgregado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdProductoNombre() {
        return idProductoNombre;
    }

    public void setIdProductoNombre(String idProductoNombre) {
        this.idProductoNombre = idProductoNombre;
    }

    public Double getIdProductoPrecio() {
        return idProductoPrecio;
    }

    public void setIdProductoPrecio(Double idProductoPrecio) {
        this.idProductoPrecio = idProductoPrecio;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "idUsuario = " + idUsuario + ", " +
                "idProducto = " + idProducto + ", " +
                "idProductoNombre = " + idProductoNombre + ", " +
                "idProductoPrecio = " + idProductoPrecio + ", " +
                "cantidad = " + cantidad + ", " +
                "fechaAgregado = " + fechaAgregado + ")";
    }
}