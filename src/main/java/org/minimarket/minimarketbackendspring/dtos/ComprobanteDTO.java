package org.minimarket.minimarketbackendspring.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * DTO for {@link org.minimarket.minimarketbackendspring.entities.Comprobante}
 */
public class ComprobanteDTO implements Serializable {
    private Long id;
    private Long idPedidoId;
    private String idPedidoIdUsuarioIdUsuario;
    private String idPedidoIdUsuarioNombre;
    private String idPedidoIdUsuarioApellido;
    private String idPedidoMetodoPago;
    private String tipo;
    private OffsetDateTime fecha;
    private BigDecimal montoTotal;

    public ComprobanteDTO() {
    }

    public ComprobanteDTO(Long id, Long idPedidoId, String idPedidoIdUsuarioIdUsuario, String idPedidoIdUsuarioNombre, String idPedidoIdUsuarioApellido, String idPedidoMetodoPago, String tipo, OffsetDateTime fecha, BigDecimal montoTotal) {
        this.id = id;
        this.idPedidoId = idPedidoId;
        this.idPedidoIdUsuarioIdUsuario = idPedidoIdUsuarioIdUsuario;
        this.idPedidoIdUsuarioNombre = idPedidoIdUsuarioNombre;
        this.idPedidoIdUsuarioApellido = idPedidoIdUsuarioApellido;
        this.idPedidoMetodoPago = idPedidoMetodoPago;
        this.tipo = tipo;
        this.fecha = fecha;
        this.montoTotal = montoTotal;
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

    public String getIdPedidoIdUsuarioIdUsuario() {
        return idPedidoIdUsuarioIdUsuario;
    }

    public void setIdPedidoIdUsuarioIdUsuario(String idPedidoIdUsuarioIdUsuario) {
        this.idPedidoIdUsuarioIdUsuario = idPedidoIdUsuarioIdUsuario;
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

    public String getIdPedidoMetodoPago() {
        return idPedidoMetodoPago;
    }

    public void setIdPedidoMetodoPago(String idPedidoMetodoPago) {
        this.idPedidoMetodoPago = idPedidoMetodoPago;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public OffsetDateTime getFecha() {
        return fecha;
    }

    public void setFecha(OffsetDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "idPedidoId = " + idPedidoId + ", " +
                "idPedidoIdUsuarioIdUsuario = " + idPedidoIdUsuarioIdUsuario + ", " +
                "idPedidoIdUsuarioNombre = " + idPedidoIdUsuarioNombre + ", " +
                "idPedidoIdUsuarioApellido = " + idPedidoIdUsuarioApellido + ", " +
                "idPedidoMetodoPago = " + idPedidoMetodoPago + ", " +
                "tipo = " + tipo + ", " +
                "fecha = " + fecha + ", " +
                "montoTotal = " + montoTotal + ")";
    }
}