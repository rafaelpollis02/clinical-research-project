package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.Autenticate;
import br.com.clinicalresearch.dto.AutenticateRequest;
import br.com.clinicalresearch.dto.AutenticateResponse;
import br.com.clinicalresearch.exceptions.*;
import br.com.clinicalresearch.service.AutenticateService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/v1/autenticate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutenticateResource {

    @Inject
    AutenticateService autenticateService;

    @GET
    @Path("/{user}/user")
    public Response getAutenticateByCpfOrEmail(@PathParam("user") String user) throws NoContentException {
        autenticateService.getAutenticateByCpfOrEmail(user);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    public Response getAccessByCpfOrEmail(AutenticateRequest autenticateRequest) throws InvalidLoginException, NoContentException {
        autenticateService.getAccessByCpfOrEmail(autenticateRequest);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/generateToken")
    @Transactional
    public Response generateToken(AutenticateRequest autenticateRequest) throws NoContentException {
        autenticateService.generateToken(autenticateRequest);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{token}/validateToken")
    public Response validateToken(@PathParam("token") String token) throws BusinessException, NoContentException {
        AutenticateResponse existingAutenticate = autenticateService.validateToken(token);
        return Response.status(Response.Status.OK).entity(existingAutenticate).build();
    }

    @PUT
    @Path("/{user}/updatePassword")
    @Transactional
    public Response updatePasswordAutenticate(@PathParam("user") String user, AutenticateRequest autenticateRequest) throws BadRequestException, NoContentException {
        autenticateService.updatePasswordAutenticate(user, autenticateRequest);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{idAutenticate}/id")
    @Transactional
    public Response updateAutenticate(@PathParam("idAutenticate") Long idAutenticate, Autenticate autenticate) throws NoContentException {
        autenticateService.updateAutenticate(idAutenticate, autenticate);
        return Response.status(Response.Status.OK).build();
    }

}
