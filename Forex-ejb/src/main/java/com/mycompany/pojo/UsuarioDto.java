/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pojo;

import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author German
 */
public class UsuarioDto {

    private Integer id;

    private String nombres;

    private String apellidos;

    private BigInteger documento;

    private String usuario;

    private String clave;

    private String correo;

    private List<UsuarioParDto> usuarioParList;

    private SaldoDto saldo;

    private String token;

    public UsuarioDto() {

    }

    public UsuarioDto(Integer id, String nombres, String apellidos, BigInteger documento, String usuario, String clave, String correo, SaldoDto saldo, String token) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.usuario = usuario;
        this.clave = clave;
        this.correo = correo;
        this.saldo = saldo;
        this.token = token;
    }

    public UsuarioDto(Integer id, String nombres, String apellidos, BigInteger documento, String usuario, String clave, String correo, List<UsuarioParDto> usuarioParList, SaldoDto saldo, String token) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.usuario = usuario;
        this.clave = clave;
        this.correo = correo;
        this.usuarioParList = usuarioParList;
        this.saldo = saldo;
        this.token = token;
    }

    public UsuarioDto(Integer id, String nombres, String apellidos, BigInteger documento, String usuario, String clave, String correo, List<UsuarioParDto> usuarioParList, SaldoDto saldo) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.usuario = usuario;
        this.clave = clave;
        this.correo = correo;
        this.usuarioParList = usuarioParList;
        this.saldo = saldo;
    }

    public UsuarioDto(Integer id, String nombres, String apellidos, BigInteger documento, String usuario, String clave, String correo, SaldoDto saldo) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.usuario = usuario;
        this.clave = clave;
        this.correo = correo;
        this.saldo = saldo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public BigInteger getDocumento() {
        return documento;
    }

    public void setDocumento(BigInteger documento) {
        this.documento = documento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<UsuarioParDto> getUsuarioParList() {
        return usuarioParList;
    }

    public void setUsuarioParList(List<UsuarioParDto> usuarioParList) {
        this.usuarioParList = usuarioParList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SaldoDto getSaldo() {
        return saldo;
    }

    public void setSaldo(SaldoDto saldo) {
        this.saldo = saldo;
    }

}
