package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.exceptions.NoContentException;
import br.com.clinicalresearch.service.EstablishmentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("api/v1/establishment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstablishmentResource {
    @Inject
    EstablishmentService establishmentService;

    @GET
    public Response getAllEstablishment() throws NoContentException {
        List<Establishment> existingEstablishment = establishmentService.getAllEstablishment();
        return Response.status(Response.Status.OK).entity(existingEstablishment).build();
    }

    @GET
    @Path("/{idEstablishment}/id")
    public Response getEstablishmentById(@PathParam("idEstablishment") Long idEstablishment) throws NoContentException {
        Establishment existingEstablishment = establishmentService.getEstablishmentById(idEstablishment);
        return Response.status(Response.Status.OK).entity(existingEstablishment).build();
    }

    @GET
    @Path("/{name}/name")
    public Response getEstablishmentByName(@PathParam("name") String name) throws NoContentException {
        List<Establishment> existingEstablishment = establishmentService.getEstablishmentByName(name);
        return Response.status(Response.Status.OK).entity(existingEstablishment).build();
    }

    @POST
    @Transactional
    public Response createEstablishment(Establishment establishment) {
        Establishment existingEstablishment = establishmentService.createEstablishment(establishment);
        return Response.status(Response.Status.CREATED).entity(existingEstablishment).build();
    }

    @PUT
    @Path("/{idEstablishment}")
    @Transactional
    public Response updateEstablishment(@PathParam("idEstablishment") Long idEstablishment, Establishment establishment) throws NoContentException {
        Establishment existingEstablishment = establishmentService.updateEstablishment(idEstablishment, establishment);
        return Response.status(Response.Status.OK).entity(existingEstablishment).build();
    }

    @DELETE
    @Path("/{idEstablishment}")
    @Transactional
    public Response deleteEstablishment(@PathParam("idEstablishment") Long idEstablishment) throws NoContentException {
        establishmentService.deleteEstablishment(idEstablishment);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
