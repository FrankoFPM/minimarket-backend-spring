package org.minimarket.minimarketbackendspring.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "PEDIDO")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PEDIDO", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario idUsuario;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "FECHA_PEDIDO")
    private OffsetDateTime fechaPedido;

    @ColumnDefault("'solicitado'")
    @Column(name = "ESTADO", length = 20)
    private String estado;

    @ColumnDefault("'contraentrega'")
    @Column(name = "METODO_PAGO", length = 20)
    private String metodoPago;

    @ColumnDefault("0")
    @Column(name = "TOTAL", precision = 10, scale = 2)
    private BigDecimal total;

    @ColumnDefault("0")
    @Column(name = "DESCUENTO_APLICADO", precision = 10, scale = 2)
    private BigDecimal descuentoAplicado;

    @ColumnDefault("0")
    @Column(name = "IMPUESTO", precision = 10, scale = 2)
    private BigDecimal impuesto;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "CREATED_BY")
    private Usuario createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "UPDATED_BY")
    private Usuario updatedBy;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATED_AT")
    private OffsetDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "UPDATED_AT")
    private OffsetDateTime updatedAt;

    @OneToOne(mappedBy = "idPedido")
    private Comprobante comprobante;

    @OneToMany(mappedBy = "idPedido")
    private Set<DetallePedido> detallePedidos = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public OffsetDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(OffsetDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public void setDescuentoAplicado(BigDecimal descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public Usuario getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Usuario createdBy) {
        this.createdBy = createdBy;
    }

    public Usuario getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Usuario updatedBy) {
        this.updatedBy = updatedBy;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public Set<DetallePedido> getDetallePedidos() {
        return detallePedidos;
    }

    public void setDetallePedidos(Set<DetallePedido> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }

}