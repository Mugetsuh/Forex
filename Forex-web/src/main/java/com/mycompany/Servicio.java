/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import com.mycompany.bean.RegistroLocal;
import com.mycompany.bean.pruebaLocal;
import com.mycompany.pojo.UsuarioDto;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
    pruebaLocal prueba;
    
    @EJB
    RegistroLocal registro;

    @POST
    @Path("/registroUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registro(UsuarioDto usuario) {
        JsonObject json = registro.registro(usuario);
        //List<Usuario> listausuario = prueba.obtenerUsuario();
//        System.out.println(respuesta);
        return Response.status(Response.Status.OK).entity(json).build();
    }

    @GET
    @Path("/prueba2")
    @Produces(MediaType.APPLICATION_JSON)
    public Response prueba2() {
        JsonObject json2 = registro.consultaRegistro();
//        Usuario usuario = new Usuario();
//        usuario.setNombres("Mauricio");
//        usuario.setApellidos("Problemas");
//        usuario.setDocumento(BigInteger.valueOf(123456));
//        usuario.setUsuario("german");
//        usuario.setClave("1234");
//        usuario.setCorreo("shin@gmail.com");
//        prueba.guardarPrueba(usuario);
        System.out.println();
        return Response.status(Response.Status.OK).entity(json2).build();
    }
}
