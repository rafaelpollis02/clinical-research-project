package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.PersonPersonType;
import br.com.clinicalresearch.dto.PersonPersonTypeRequest;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NoContentException;
import br.com.clinicalresearch.service.PersonPersonTypeService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("api/v1/personPersonType")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonPersonTypeResource {


    @Inject
    PersonPersonTypeService personPersonTypeService;

    @GET
    public Response getAllPersonPersonType() throws NoContentException {
        List<PersonPersonType> existingPersonPersonType = personPersonTypeService.getAllPersonPersonType();
        return Response.status(Response.Status.OK).entity(existingPersonPersonType).build();
    }

    @GET
    @Path("/{idPersonPersonType}/id")
    public Response getPersonPersonTypeById(@PathParam("idPersonPersonType") Long idPersonPersonType) throws NoContentException {
        PersonPersonType existingPersonPersonType = personPersonTypeService.getPersonPersonTypeById(idPersonPersonType);
        return Response.status(Response.Status.OK).entity(existingPersonPersonType).build();
    }

    @PUT
    @Path("/{idPersonPersonType}/id")
    @Transactional
    public Response updatePersonPersonType(@PathParam("idPersonPersonType") Long idPersonPersonType, PersonPersonType personPersonType) throws NoContentException {
        personPersonTypeService.updatePersonPersonType(idPersonPersonType, personPersonType);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Transactional
    public Response addPersonPersonType(PersonPersonTypeRequest personPersonTypeRequest) throws BusinessException, NoContentException {
        PersonPersonType existingPersonPersonType = personPersonTypeService.addPersonPersonType(personPersonTypeRequest);
        return Response.status(Response.Status.CREATED).entity(existingPersonPersonType).build();
    }

    @DELETE
    @Path("/{idPersonPersonType}/id")
    @Transactional
    public Response removePersonPersonType(@PathParam("idPersonPersonType") Long idPersonPersonType) throws NoContentException {
        personPersonTypeService.removePersonPersonType(idPersonPersonType);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
