package org.minimarket.minimarketbackendspring.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
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
    
    private BigDecimal precioOriginal;
    private BigDecimal montoDescuento;
    private BigDecimal porcentajeDescuento;
    private Boolean tieneDescuento;
    private BigDecimal precioConDescuento;

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
        this.tieneDescuento = false;
        this.montoDescuento = BigDecimal.ZERO;
        this.porcentajeDescuento = BigDecimal.ZERO;
        this.precioOriginal = idProductoPrecio != null ? BigDecimal.valueOf(idProductoPrecio) : BigDecimal.ZERO;
        this.precioConDescuento = this.precioOriginal;
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

    public BigDecimal getPrecioOriginal() {
        return precioOriginal;
    }

    public void setPrecioOriginal(BigDecimal precioOriginal) {
        this.precioOriginal = precioOriginal;
    }

    public BigDecimal getMontoDescuento() {
        return montoDescuento;
    }

    public void setMontoDescuento(BigDecimal montoDescuento) {
        this.montoDescuento = montoDescuento;
    }

    public BigDecimal getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public BigDecimal getPrecioConDescuento() {
        return precioConDescuento;
    }

    public void setPrecioConDescuento(BigDecimal precioConDescuento) {
        this.precioConDescuento = precioConDescuento;
    }

    public Boolean getTieneDescuento() {
        return tieneDescuento;
    }

    public void setTieneDescuento(Boolean tieneDescuento) {
        this.tieneDescuento = tieneDescuento;
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
                "fechaAgregado = " + fechaAgregado + ", " +
                "tieneDescuento = " + tieneDescuento + ", " +
                "precioOriginal = " + precioOriginal + ", " +
                "precioConDescuento = " + precioConDescuento + ", " +
                "montoDescuento = " + montoDescuento + ", " +
                "porcentajeDescuento = " + porcentajeDescuento + ")";
    }
}