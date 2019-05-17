/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author German
 */
@Entity
@Table(name = "valor_par")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValorPar.findAll", query = "SELECT v FROM ValorPar v"),
    @NamedQuery(name = "ValorPar.findById", query = "SELECT v FROM ValorPar v WHERE v.id = :id"),
    @NamedQuery(name = "ValorPar.findByValor", query = "SELECT v FROM ValorPar v WHERE v.valor = :valor"),
    @NamedQuery(name = "ValorPar.findByFecha", query = "SELECT v FROM ValorPar v WHERE v.fecha = :fecha")})
public class ValorPar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Float valor;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "pk_par", referencedColumnName = "id")
    @ManyToOne
    private Par pkPar;

    public ValorPar() {
    }

    public ValorPar(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Par getPkPar() {
        return pkPar;
    }

    public void setPkPar(Par pkPar) {
        this.pkPar = pkPar;
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
        if (!(object instanceof ValorPar)) {
            return false;
        }
        ValorPar other = (ValorPar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.ValorPar[ id=" + id + " ]";
    }
    
}
