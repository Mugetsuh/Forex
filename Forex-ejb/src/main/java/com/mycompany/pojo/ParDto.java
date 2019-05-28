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
public class ParDto {

    private Integer id;

    private String nombre;

    private List<UsuarioParDto> usuarioParList;

    private List<ValorParDto> valorParList;

    public ParDto(Integer id, String nombre, List<UsuarioParDto> usuarioParList, List<ValorParDto> valorParList) {
        this.id = id;
        this.nombre = nombre;
        this.usuarioParList = usuarioParList;
        this.valorParList = valorParList;
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

    public List<UsuarioParDto> getUsuarioParList() {
        return usuarioParList;
    }

    public void setUsuarioParList(List<UsuarioParDto> usuarioParList) {
        this.usuarioParList = usuarioParList;
    }

    public List<ValorParDto> getValorParList() {
        return valorParList;
    }

    public void setValorParList(List<ValorParDto> valorParList) {
        this.valorParList = valorParList;
    }
}
