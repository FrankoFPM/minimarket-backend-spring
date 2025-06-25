package org.minimarket.minimarketbackendspring.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link org.minimarket.minimarketbackendspring.entities.DetallePedido}
 */
public class DetallePedidoDTO implements Serializable {
    private Long id;
    private Long idPedidoId;
    private String idPedidoIdUsuarioNombre;
    private String idPedidoIdUsuarioApellido;
    private String idProducto;
    private String idProductoNombre;
    private String idProductoFoto;
    private String idProductoDescripcion;
    private Long cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public DetallePedidoDTO() {
    }

    public DetallePedidoDTO(Long id, Long idPedidoId, String idPedidoIdUsuarioNombre, String idPedidoIdUsuarioApellido, String idProducto, String idProductoNombre, String idProductoFoto, String idProductoDescripcion, Long cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.id = id;
        this.idPedidoId = idPedidoId;
        this.idPedidoIdUsuarioNombre = idPedidoIdUsuarioNombre;
        this.idPedidoIdUsuarioApellido = idPedidoIdUsuarioApellido;
        this.idProducto = idProducto;
        this.idProductoNombre = idProductoNombre;
        this.idProductoFoto = idProductoFoto;
        this.idProductoDescripcion = idProductoDescripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPedidoId() {
        return idPedidoId;
    }

    public void setIdPedidoId(Long idPedidoId) {
        this.idPedidoId = idPedidoId;
    }

    public String getIdPedidoIdUsuarioNombre() {
        return idPedidoIdUsuarioNombre;
    }

    public void setIdPedidoIdUsuarioNombre(String idPedidoIdUsuarioNombre) {
        this.idPedidoIdUsuarioNombre = idPedidoIdUsuarioNombre;
    }

    public String getIdPedidoIdUsuarioApellido() {
        return idPedidoIdUsuarioApellido;
    }

    public void setIdPedidoIdUsuarioApellido(String idPedidoIdUsuarioApellido) {
        this.idPedidoIdUsuarioApellido = idPedidoIdUsuarioApellido;
    }

    public String getIdProductoNombre() {
        return idProductoNombre;
    }

    public void setIdProductoNombre(String idProductoNombre) {
        this.idProductoNombre = idProductoNombre;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdProductoFoto() {
        return idProductoFoto;
    }

    public void setIdProductoFoto(String idProductoFoto) {
        this.idProductoFoto = idProductoFoto;
    }

    public String getIdProductoDescripcion() {
        return idProductoDescripcion;
    }

    public void setIdProductoDescripcion(String idProductoDescripcion) {
        this.idProductoDescripcion = idProductoDescripcion;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "idPedidoId = " + idPedidoId + ", " +
                "idPedidoIdUsuarioNombre = " + idPedidoIdUsuarioNombre + ", " +
                "idPedidoIdUsuarioApellido = " + idPedidoIdUsuarioApellido + ", " +
                "idProductoNombre = " + idProductoNombre + ", " +
                "cantidad = " + cantidad + ", " +
                "precioUnitario = " + precioUnitario + ", " +
                "subtotal = " + subtotal + ")";
    }
}