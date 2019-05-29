/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bean;

import com.mycompany.controller.UsuarioJpaController;
import com.mycompany.entities.Usuario;
import com.mycompany.pojo.UsuarioDto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author German
 */
@Stateless
public class Registro implements RegistroLocal {

    @Override
    public JsonObject registro(UsuarioDto usuario) {
        JsonObject json = Json.createObjectBuilder()
                .add("Mensaje", "Credenciales Incorrectas")
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
            json = Json.createObjectBuilder()
                    .add("Mensaje", "Registro Exitoso")
                    .build();
        } catch (Exception ex) {
            Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }


    public List<Usuario> consultaRegistro() {
        List<Usuario> u = new ArrayList<>();
        try {
            UsuarioJpaController jpa = new UsuarioJpaController();
            u = jpa.findUsuarioEntities();
        } catch (Exception ex) {
            Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }
}
