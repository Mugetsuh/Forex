/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author German
 */
@Entity
@Table(name = "usuario_par")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioPar.findAll", query = "SELECT u FROM UsuarioPar u"),
    @NamedQuery(name = "UsuarioPar.findById", query = "SELECT u FROM UsuarioPar u WHERE u.id = :id"),
    @NamedQuery(name = "UsuarioPar.findByValorComprado", query = "SELECT u FROM UsuarioPar u WHERE u.valorComprado = :valorComprado"),
    @NamedQuery(name = "UsuarioPar.findByValorRelacion", query = "SELECT u FROM UsuarioPar u WHERE u.valorRelacion = :valorRelacion"),
    @NamedQuery(name = "UsuarioPar.findByEstado", query = "SELECT u FROM UsuarioPar u WHERE u.estado = :estado")})
public class UsuarioPar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_comprado")
    private Float valorComprado;
    @Column(name = "valor_relacion")
    private Float valorRelacion;
    @Column(name = "estado")
    private Boolean estado;
    @OneToMany(mappedBy = "fkUsuarioPar")
    private List<Movimiento> movimientoList;
    @JoinColumn(name = "id_par", referencedColumnName = "id")
    @ManyToOne
    private Par idPar;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario idUsuario;

    public UsuarioPar() {
    }

    public UsuarioPar(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getValorComprado() {
        return valorComprado;
    }

    public void setValorComprado(Float valorComprado) {
        this.valorComprado = valorComprado;
    }

    public Float getValorRelacion() {
        return valorRelacion;
    }

    public void setValorRelacion(Float valorRelacion) {
        this.valorRelacion = valorRelacion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Movimiento> getMovimientoList() {
        return movimientoList;
    }

    public void setMovimientoList(List<Movimiento> movimientoList) {
        this.movimientoList = movimientoList;
    }

    public Par getIdPar() {
        return idPar;
    }

    public void setIdPar(Par idPar) {
        this.idPar = idPar;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioPar)) {
            return false;
        }
        UsuarioPar other = (UsuarioPar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.UsuarioPar[ id=" + id + " ]";
    }
    
}
