/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bean;

import com.mycompany.entities.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author German
 */
@Local
public interface pruebaLocal {
    
    public void guardarPrueba(Usuario usuario);
    
    public List<Usuario> obtenerUsuario();
}
