package org.minimarket.minimarketbackendspring.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "CARRITO_TEMPORAL")
public class CarritoTemporal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CARRITO", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario idUsuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_PRODUCTO", nullable = false)
    private Producto idProducto;

    @Column(name = "CANTIDAD", nullable = false)
    private Long cantidad;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "FECHA_AGREGADO")
    private Instant fechaAgregado;

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

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
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

}