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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author German
 */
@Entity
@Table(name = "par")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Par.findAll", query = "SELECT p FROM Par p"),
    @NamedQuery(name = "Par.findById", query = "SELECT p FROM Par p WHERE p.id = :id"),
    @NamedQuery(name = "Par.findByNombre", query = "SELECT p FROM Par p WHERE p.nombre = :nombre")})
public class Par implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idPar")
    private List<UsuarioPar> usuarioParList;
    @OneToMany(mappedBy = "pkPar")
    private List<ValorPar> valorParList;

    public Par() {
    }

    public Par(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<UsuarioPar> getUsuarioParList() {
        return usuarioParList;
    }

    public void setUsuarioParList(List<UsuarioPar> usuarioParList) {
        this.usuarioParList = usuarioParList;
    }

    @XmlTransient
    public List<ValorPar> getValorParList() {
        return valorParList;
    }

    public void setValorParList(List<ValorPar> valorParList) {
        this.valorParList = valorParList;
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
        if (!(object instanceof Par)) {
            return false;
        }
        Par other = (Par) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entities.Par[ id=" + id + " ]";
    }
    
}
