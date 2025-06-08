package org.minimarket.minimarketbackendspring.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link org.minimarket.minimarketbackendspring.entities.DescuentoPromocion}
 */
public class DescuentoPromocionDTO implements Serializable {
    private Long id;
    private String idProductoIdProducto;
    private String idProductoNombre;
    private String descripcion;
    private BigDecimal porcentaje;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;

    public DescuentoPromocionDTO() {
    }

    public DescuentoPromocionDTO(Long id, String idProductoIdProducto, String idProductoNombre, String descripcion, BigDecimal porcentaje, LocalDate fechaInicio, LocalDate fechaFin, String estado) {
        this.id = id;
        this.idProductoIdProducto = idProductoIdProducto;
        this.idProductoNombre = idProductoNombre;
        this.descripcion = descripcion;
        this.porcentaje = porcentaje;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "idProductoIdProducto = " + idProductoIdProducto + ", " +
                "idProductoNombre = " + idProductoNombre + ", " +
                "descripcion = " + descripcion + ", " +
                "porcentaje = " + porcentaje + ", " +
                "fechaInicio = " + fechaInicio + ", " +
                "fechaFin = " + fechaFin + ", " +
                "estado = " + estado + ")";
    }
}