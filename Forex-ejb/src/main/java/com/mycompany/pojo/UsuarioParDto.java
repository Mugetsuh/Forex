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
public class UsuarioParDto {

    private Integer id;

    private Float valorComprado;

    private Float valorRelacion;

    private Boolean estado;

    private List<MovimientoDto> movimientoList;

    private ParDto idPar;

    private UsuarioDto idUsuario;

    public UsuarioParDto(Integer id, Float valorComprado, Float valorRelacion, Boolean estado, List<MovimientoDto> movimientoList, ParDto idPar, UsuarioDto idUsuario) {
        this.id = id;
        this.valorComprado = valorComprado;
        this.valorRelacion = valorRelacion;
        this.estado = estado;
        this.movimientoList = movimientoList;
        this.idPar = idPar;
        this.idUsuario = idUsuario;
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

    public List<MovimientoDto> getMovimientoList() {
        return movimientoList;
    }

    public void setMovimientoList(List<MovimientoDto> movimientoList) {
        this.movimientoList = movimientoList;
    }

    public ParDto getIdPar() {
        return idPar;
    }

    public void setIdPar(ParDto idPar) {
        this.idPar = idPar;
    }

    public UsuarioDto getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UsuarioDto idUsuario) {
        this.idUsuario = idUsuario;
    }
}
