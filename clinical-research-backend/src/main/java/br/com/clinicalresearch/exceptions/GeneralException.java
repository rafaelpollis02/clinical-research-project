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

        if (e instanceof InvalidLoginException) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if (e instanceof BusinessException) {
            message.setType("Alerta");
            message.setMessage(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
        if (e instanceof NoContentException) {
            return Response.status(Response.Status.NO_CONTENT).build();

        } else {
            message.setMessage("Serviço indisponível: " + e.getMessage());
            message.setType("Erro");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
        }
    }
}
