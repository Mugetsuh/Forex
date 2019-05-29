/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bean;

import com.mycompany.controller.UsuarioJpaController;
import com.mycompany.entities.Usuario;
import com.mycompany.pojo.UsuarioDto;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author German
 */
@Stateful
public class Registro implements RegistroLocal {

    @Override
    public JsonObject registro(UsuarioDto usuario) {
        JsonObject json = Json.createObjectBuilder()
                .add("mensaje", "credenciales incorrectas")
                .build();
        try {
            UsuarioJpaController jpa = new UsuarioJpaController();
            Usuario u = new Usuario();
            u.setId(usuario.getId());
            u.setNombres(usuario.getNombres());
            u.setApellidos(usuario.getApellidos());
            u.setDocumento(usuario.getDocumento());
            u.setUsuario(usuario.getUsuario());
            u.setClave(usuario.getClave());
            u.setCorreo(usuario.getCorreo());
            u.setToken(usuario.getToken());
            jpa.create(u);
//            json = Json.createObjectBuilder()
//                    .add("tokenAuto", "id" + usuario.getId() + "nombres" + usuario.getNombres()
//                            + "apellidos" + usuario.getApellidos() + "documento" + usuario.getDocumento()
//                            + "usuario" + usuario.getUsuario() + "clave" + usuario.getClave()
//                            + "correo" + usuario.getCorreo() + usuario.getToken())
//                    .build();
        } catch (Exception ex) {
            Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }

    @Override
    public JsonObject consultaRegistro() {
        JsonObject json = Json.createObjectBuilder()
                .add("mensaje", "credenciales incorrectas")
                .build();
        UsuarioJpaController jpa = new UsuarioJpaController();
        Usuario u = jpa.findUsuario(1);
        System.out.println(u.getId() + " " + u.getNombres());
        json = Json.createObjectBuilder()
                .add("id", u.getId())
                .add("nombres", u.getNombres())
                .add("apellidos", u.getApellidos())
                .add("documento", u.getDocumento())
                .add("usuario", u.getUsuario())
                .add("clave", u.getClave())
                .add("correo", u.getCorreo())
                .add("token", u.getToken())
                .build();
        return json;
    }
}
