/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import com.mycompany.bean.pruebaLocal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author German
 */
@javax.enterprise.context.RequestScoped
@Path("servicio")
public class Servicio {

    @EJB
    pruebaLocal prueba;

    @GET
    @Path("/prueba")
    @Produces(MediaType.APPLICATION_JSON)
    public Response prueba() {
        List<Usuario> listausuario = prueba.obtenerUsuario();
        System.out.println();
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/prueba2")
    @Produces(MediaType.APPLICATION_JSON)
    public Response prueba2() {
        Usuario usuario = new Usuario();
        usuario.setNombres("Mauricio");
        usuario.setApellidos("Problemas");
        usuario.setDocumento(BigInteger.valueOf(123456));
        usuario.setUsuario("german");
        usuario.setClave("1234");
        usuario.setCorreo("shin@gmail.com");
        prueba.guardarPrueba(usuario);
        System.out.println();
        return Response.status(Response.Status.OK).build();
    }
}
