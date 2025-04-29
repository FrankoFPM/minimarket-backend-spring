package org.minimarket.minimarketbackendspring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "DISTRITO")
public class Distrito {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISTRITO_id_gen")
    @SequenceGenerator(name = "DISTRITO_id_gen", sequenceName = "ISEQ$$_76643", allocationSize = 1)
    @Column(name = "ID_DISTRITO", nullable = false)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_DEPARTAMENTO", nullable = false)
    @JsonBackReference
    private org.minimarket.minimarketbackendspring.entities.Departamento idDepartamento;

    @ColumnDefault("'Activo'")
    @Column(name = "ESTADO", nullable = false, length = 10)
    private String estado;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATED_AT")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "UPDATED_AT")
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "CREATED_BY")
    private org.minimarket.minimarketbackendspring.entities.Usuario createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "UPDATE_BY")
    private org.minimarket.minimarketbackendspring.entities.Usuario updateBy;

    @OneToMany(mappedBy = "idDistrito")
    @JsonManagedReference
    private Set<org.minimarket.minimarketbackendspring.entities.Usuario> usuarios = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public org.minimarket.minimarketbackendspring.entities.Departamento getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(org.minimarket.minimarketbackendspring.entities.Departamento idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public org.minimarket.minimarketbackendspring.entities.Usuario getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(org.minimarket.minimarketbackendspring.entities.Usuario createdBy) {
        this.createdBy = createdBy;
    }

    public org.minimarket.minimarketbackendspring.entities.Usuario getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(org.minimarket.minimarketbackendspring.entities.Usuario updateBy) {
        this.updateBy = updateBy;
    }

    public Set<org.minimarket.minimarketbackendspring.entities.Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<org.minimarket.minimarketbackendspring.entities.Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}