package br.com.clinicalresearch.exceptions;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class GeneralException implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {
        Message message = new Message();

        if (e instanceof BusinessException) {
            message.setMessage(e.getMessage());
            message.setType("Alert");
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        } else {
            message.setMessage("Serviço Indisponível : " + e.getMessage());
            message.setType("Error");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
        }
    }
}
