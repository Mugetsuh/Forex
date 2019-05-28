/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import com.mycompany.pojo.UsuarioDto;
import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author German
 */
@Provider
@PreMatching
public class Interceptor implements ContainerRequestFilter {

    UsuarioDto usuario = new UsuarioDto();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String url = requestContext.getUriInfo().getAbsolutePath().toString();
        if (url.contains("api/autho")) {
            return;
        }
        String token = requestContext.getHeaderString("tokenAuto");
        if (token == null) {
            JsonObject json = Json.createObjectBuilder()
                    .add("mensaje", "token requerido")
                    .build();
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity(json)
                    .type(MediaType.APPLICATION_JSON)
                    .build());

//        } else if (usuario.decodificar(token) == false) {
//            JsonObject json = Json.createObjectBuilder()
//                    .add("mensaje", "token incorrecto")
//                    .build();
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
//                    .entity(json)
//                    .type(MediaType.APPLICATION_JSON)
//                    .build());
//            
        }
    }
}