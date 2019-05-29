/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bean;

import com.mycompany.entities.Usuario;
import com.mycompany.pojo.UsuarioDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author German
 */
@Stateless
public class Login implements LoginLocal {
    @EJB
    private Registro consulta;

    public JsonObject login(String user, String pass) {
        JsonObject json = Json.createObjectBuilder()
                .add("Mensaje", "Usuario No Registrado")
                .build();
        List<Usuario> u = new ArrayList<>();
        u = consulta.consultaRegistro();
        for (Usuario usuario : u) {
            if (usuario.getUsuario().equals(user) && usuario.getClave().equals(pass)) {
                String KEY = "mi_clave";
                long tiempo = System.currentTimeMillis();
                String jwt = Jwts.builder()
                        .signWith(SignatureAlgorithm.HS256, KEY)
                        .setExpiration(new Date(tiempo + 900000))
                        .setSubject(usuario.getUsuario())
                        .claim("grupo", "estudiante")
                        .compact();
                json = Json.createObjectBuilder()
                        .add("token-auto", jwt)
                        .build();
                usuario.setToken(jwt);
                System.out.println(usuario.getToken());
                return json;
            }
        }
        return json;
    }
}
