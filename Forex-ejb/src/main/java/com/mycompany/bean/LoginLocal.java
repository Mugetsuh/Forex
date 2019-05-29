/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bean;

import com.mycompany.pojo.UsuarioDto;
import java.util.List;
import javax.ejb.Local;
import javax.json.JsonObject;

/**
 *
 * @author German
 */
@Local
public interface LoginLocal {
    
    public JsonObject login(String user, String pass);
}
