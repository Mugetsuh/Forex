/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bean;

import com.mycompany.entities.Usuario;
import java.util.logging.Level;
import javax.ejb.Stateless;
import com.mycompany.controller.UsuarioJpaController;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Logger;
/**
 *
 * @author German
 */
@Stateless
public class prueba implements pruebaLocal {

    @Override
    public void guardarPrueba(Usuario usuario){
        try {
            
            UsuarioJpaController jpa = new UsuarioJpaController();
            jpa.create(usuario);
        } catch (Exception ex) {
            Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
        }             
    }
    
    @Override
    public List<Usuario> obtenerUsuario() {
        UsuarioJpaController jpa = new UsuarioJpaController();
        List<Usuario> listaUsusario = jpa.findUsuarioEntities();
        return listaUsusario;
    }
}
