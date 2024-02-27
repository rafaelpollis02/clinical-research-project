package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.AutenticateEstablishment;
import br.com.clinicalresearch.dto.AutenticateEstablishmentRequest;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NoContentException;
import br.com.clinicalresearch.service.AutenticateEstablishmentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("api/v1/autenticateEstablishment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutenticateEstablishmentResource {

    @Inject
    AutenticateEstablishmentService autenticateEstablishmentService;

    @GET
    public Response getAllAutenticateEstablishment() throws NoContentException {
        List<AutenticateEstablishment> existingAutenticateEstablishment = autenticateEstablishmentService.getAllAutenticateEstablishment();
        return Response.status(Response.Status.OK).entity(existingAutenticateEstablishment).build();
    }

    @GET
    @Path("/{idAutenticateEstablishment}/id")
    public Response getEstablishmentById(@PathParam("idAutenticateEstablishment") Long idAutenticateEstablishment) throws NoContentException {
        AutenticateEstablishment existingAutenticateEstablishment = autenticateEstablishmentService.getAutenticateEstablishmentById(idAutenticateEstablishment);
        return Response.status(Response.Status.OK).entity(existingAutenticateEstablishment).build();
    }

    @PUT
    @Path("/{idAutenticateEstablishment}")
    @Transactional
    public Response updateAutenticateEstablishment(@PathParam("idAutenticateEstablishment") Long idAutenticateEstablishment, AutenticateEstablishment autenticateEstablishment) throws NoContentException {
        autenticateEstablishmentService.updateAutenticateEstablishment(idAutenticateEstablishment, autenticateEstablishment);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Transactional
    public Response addAutenticateEstablishment(AutenticateEstablishmentRequest autenticateEstablishmentRequest) throws BusinessException, NoContentException {
        AutenticateEstablishment existingAutenticateEstablishment = autenticateEstablishmentService.addAutenticateEstablishment(autenticateEstablishmentRequest);
        return Response.status(Response.Status.CREATED).entity(existingAutenticateEstablishment).build();
    }

    @DELETE
    @Path("/{idAutenticateEstablishment}")
    @Transactional
    public Response removeAutenticateEstablishment(@PathParam("idAutenticateEstablishment") Long idAutenticateEstablishment) throws NoContentException {
        autenticateEstablishmentService.removeAutenticateEstablishment(idAutenticateEstablishment);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
