package org.minimarket.minimarketbackendspring.dtos;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link org.minimarket.minimarketbackendspring.entities.CarritoTemporal}
 */
public class CarritoTemporalDto implements Serializable {
    private Long id;
    private String idUsuarioIdUsuario;
    private String idProductoIdProducto;
    private String idProductoNombre;
    private Double idProductoPrecio;
    private Long cantidad;
    private Instant fechaAgregado;

    public CarritoTemporalDto() {
    }

    public CarritoTemporalDto(Long id, String idUsuarioIdUsuario, String idProductoIdProducto, String idProductoNombre, Double idProductoPrecio, Long cantidad, Instant fechaAgregado) {
        this.id = id;
        this.idUsuarioIdUsuario = idUsuarioIdUsuario;
        this.idProductoIdProducto = idProductoIdProducto;
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

    public String getIdUsuarioIdUsuario() {
        return idUsuarioIdUsuario;
    }

    public void setIdUsuarioIdUsuario(String idUsuarioIdUsuario) {
        this.idUsuarioIdUsuario = idUsuarioIdUsuario;
    }

    public String getIdProductoIdProducto() {
        return idProductoIdProducto;
    }

    public void setIdProductoIdProducto(String idProductoIdProducto) {
        this.idProductoIdProducto = idProductoIdProducto;
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

    public Instant getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(Instant fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "idUsuarioIdUsuario = " + idUsuarioIdUsuario + ", " +
                "idProductoIdProducto = " + idProductoIdProducto + ", " +
                "idProductoNombre = " + idProductoNombre + ", " +
                "idProductoPrecio = " + idProductoPrecio + ", " +
                "cantidad = " + cantidad + ", " +
                "fechaAgregado = " + fechaAgregado + ")";
    }
}