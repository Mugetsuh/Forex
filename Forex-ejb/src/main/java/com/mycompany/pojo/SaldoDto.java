/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pojo;

import java.util.List;

/**
 *
 * @author German
 */
public class SaldoDto {

    private Integer id;

    private String total;

    private Integer fkMovimiento;

    private List<MovimientoDto> movimientoList;

    private UsuarioDto usuario;

    public SaldoDto(Integer id, String total, Integer fkMovimiento, List<MovimientoDto> movimientoList, UsuarioDto usuario) {
        this.id = id;
        this.total = total;
        this.fkMovimiento = fkMovimiento;
        this.movimientoList = movimientoList;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getFkMovimiento() {
        return fkMovimiento;
    }

    public void setFkMovimiento(Integer fkMovimiento) {
        this.fkMovimiento = fkMovimiento;
    }

    public List<MovimientoDto> getMovimientoList() {
        return movimientoList;
    }

    public void setMovimientoList(List<MovimientoDto> movimientoList) {
        this.movimientoList = movimientoList;
    }

    public UsuarioDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDto usuario) {
        this.usuario = usuario;
    }
}
