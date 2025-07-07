package org.minimarket.minimarketbackendspring.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * DTO for {@link org.minimarket.minimarketbackendspring.entities.Pedido}
 */
public class PedidoDTO implements Serializable {
    private Long id;
    private String idUsuarioIdUsuario;
    private String idUsuarioNombre;
    private String idUsuarioApellido;
    private OffsetDateTime fechaPedido;
    private String estado;
    private String metodoPago;
    private BigDecimal total;
    private BigDecimal descuentoAplicado;
    private BigDecimal impuesto;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Long comprobanteId;

    public PedidoDTO(Long id, String idUsuarioIdUsuario, String idUsuarioNombre, String idUsuarioApellido, OffsetDateTime fechaPedido, String estado, String metodoPago, BigDecimal total, BigDecimal descuentoAplicado, BigDecimal impuesto, OffsetDateTime createdAt, OffsetDateTime updatedAt, Long comprobanteId) {
        this.id = id;
        this.idUsuarioIdUsuario = idUsuarioIdUsuario;
        this.idUsuarioNombre = idUsuarioNombre;
        this.idUsuarioApellido = idUsuarioApellido;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.total = total;
        this.descuentoAplicado = descuentoAplicado;
        this.impuesto = impuesto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comprobanteId = comprobanteId;
    }

    public Long getId() {
        return id;
    }

    public String getIdUsuarioNombre() {
        return idUsuarioNombre;
    }

    public String getIdUsuarioApellido() {
        return idUsuarioApellido;
    }

    public OffsetDateTime getFechaPedido() {
        return fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getComprobanteId() {
        return comprobanteId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdUsuarioNombre(String idUsuarioNombre) {
        this.idUsuarioNombre = idUsuarioNombre;
    }

    public void setIdUsuarioApellido(String idUsuarioApellido) {
        this.idUsuarioApellido = idUsuarioApellido;
    }

    public void setFechaPedido(OffsetDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setDescuentoAplicado(BigDecimal descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setComprobanteId(Long comprobanteId) {
        this.comprobanteId = comprobanteId;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "idUsuarioNombre = " + idUsuarioNombre + ", " +
                "idUsuarioApellido = " + idUsuarioApellido + ", " +
                "fechaPedido = " + fechaPedido + ", " +
                "estado = " + estado + ", " +
                "metodoPago = " + metodoPago + ", " +
                "total = " + total + ", " +
                "descuentoAplicado = " + descuentoAplicado + ", " +
                "impuesto = " + impuesto + ", " +
                "createdAt = " + createdAt + ", " +
                "updatedAt = " + updatedAt + ", " +
                "comprobanteId = " + comprobanteId + ")";
    }

    public String getIdUsuarioIdUsuario() {
        return idUsuarioIdUsuario;
    }

    public void setIdUsuarioIdUsuario(String idUsuarioIdUsuario) {
        this.idUsuarioIdUsuario = idUsuarioIdUsuario;
    }
}