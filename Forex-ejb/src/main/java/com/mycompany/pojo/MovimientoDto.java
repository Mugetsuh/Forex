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
public class MovimientoDto {

    private Integer id;

    private Date fecha;

    private Float total;

    private String descripcion;

    private SaldoDto fkSaldo;

    private UsuarioParDto fkUsuarioPar;

    public MovimientoDto(Integer id, Date fecha, Float total, String descripcion, SaldoDto fkSaldo, UsuarioParDto fkUsuarioPar) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.descripcion = descripcion;
        this.fkSaldo = fkSaldo;
        this.fkUsuarioPar = fkUsuarioPar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public SaldoDto getFkSaldo() {
        return fkSaldo;
    }

    public void setFkSaldo(SaldoDto fkSaldo) {
        this.fkSaldo = fkSaldo;
    }

    public UsuarioParDto getFkUsuarioPar() {
        return fkUsuarioPar;
    }

    public void setFkUsuarioPar(UsuarioParDto fkUsuarioPar) {
        this.fkUsuarioPar = fkUsuarioPar;
    }
}
