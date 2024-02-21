package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.dto.AutenticateRequest;
import br.com.clinicalresearch.dto.AutenticateResponse;
import br.com.clinicalresearch.exceptions.BadRequestException;
import br.com.clinicalresearch.exceptions.InvalidLoginException;
import br.com.clinicalresearch.exceptions.NotFoundException;
import br.com.clinicalresearch.service.AutenticateService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.annotations.Param;

@Path("api/v1/autenticate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutenticateResource {

    @Inject
    AutenticateService autenticateService;

    @GET
    @Path("/{user}")
    public Response getAutenticateByCpfOrEmail(@PathParam("user") String user) throws NotFoundException {
        autenticateService.getAutenticateByCpfOrEmail(user);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    public Response getAccessByCpfOrEmail(AutenticateRequest autenticateRequest) throws InvalidLoginException, NotFoundException {
        autenticateService.getAccessByCpfOrEmail(autenticateRequest);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/generateToken")
    @Transactional
    public Response generateToken(AutenticateRequest autenticateRequest) throws NotFoundException {
        autenticateService.generateToken(autenticateRequest);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{token}/validateToken")
    public Response validateToken(@PathParam("token") String token) throws BadRequestException, NotFoundException {
        AutenticateResponse existingAutenticate = autenticateService.validateToken(token);
        return Response.status(Response.Status.OK).entity(existingAutenticate).build();
    }

    @PUT
    @Path("/{user}")
    @Transactional
    public Response updatePasswordAutenticate(@PathParam("user") String user, AutenticateRequest autenticateRequest) throws BadRequestException, NotFoundException {
        autenticateService.updatePasswordAutenticate(user, autenticateRequest);
        return Response.status(Response.Status.OK).build();
    }

}
