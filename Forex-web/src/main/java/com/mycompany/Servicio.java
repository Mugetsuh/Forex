/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import com.mycompany.bean.LoginLocal;
import com.mycompany.bean.RegistroLocal;
import com.mycompany.pojo.UsuarioDto;
import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author German
 */
@javax.enterprise.context.RequestScoped
@Path("registro")
public class Servicio {

    @EJB
    RegistroLocal registro;
    
    @EJB
    LoginLocal login;

    @POST
    @Path("/registroUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registro(UsuarioDto usuario) {
        JsonObject json = registro.registro(usuario);
        return Response.status(Response.Status.OK).entity(json).build();
    }

    @POST
    @Path("/loginUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUsuario(UsuarioDto usuario) {
        JsonObject json2 = login.login(usuario.getUsuario(), usuario.getClave());
        return Response.status(Response.Status.OK).entity(json2).build();
    }
}
