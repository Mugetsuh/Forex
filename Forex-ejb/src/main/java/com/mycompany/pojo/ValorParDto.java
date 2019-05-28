/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pojo;

import java.util.Date;

/**
 *
 * @author German
 */
public class ValorParDto {

    private Integer id;

    private Float valor;

    private Date fecha;

    private ParDto pkPar;

    public ValorParDto(Integer id, Float valor, Date fecha, ParDto pkPar) {
        this.id = id;
        this.valor = valor;
        this.fecha = fecha;
        this.pkPar = pkPar;
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

    public ParDto getPkPar() {
        return pkPar;
    }

    public void setPkPar(ParDto pkPar) {
        this.pkPar = pkPar;
    }    
}
