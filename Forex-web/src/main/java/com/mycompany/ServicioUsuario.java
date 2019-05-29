/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import com.mycompany.bean.LoginLocal;
import com.mycompany.pojo.UsuarioDto;
import java.util.List;
import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author German
 */
@javax.enterprise.context.RequestScoped
@Path("autho")
public class ServicioUsuario {
    
    @EJB
    LoginLocal login;        
    UsuarioDto usuario = new UsuarioDto();

//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/lista")    
//    public Response listarEstudiantes() {
//        //return Response.status(Response.Status.OK).build();
//        return Response.status(Response.Status.OK).entity(usuario.getListaUsuario()).build();
//    }
    
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("login")    
//    public Response validarUsuario(UsuarioDto usuario) {  
////        JsonObject json = usuario.login(usuario);
////        List<UsuarioDto> lis = login.login(usuario);
////        return Response.status(Response.Status.OK).entity(lis).build();
////        if (json.containsKey("mensaje")) {           
////            return Response.status(Response.Status.BAD_REQUEST).entity(json).build();
////        } else {
////            return Response.status(Response.Status.OK).entity(json).build();
////        }              
//    }
}
