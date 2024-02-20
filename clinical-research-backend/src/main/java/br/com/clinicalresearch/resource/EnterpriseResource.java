package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.Enterprise;
import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NotFoundException;
import br.com.clinicalresearch.service.EnterpriseService;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("api/v1/enterprise")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnterpriseResource {
    @Inject
    EnterpriseService enterpriseService;

    @GET
    public Response getAllEnterprise() {
         List<Enterprise> existingEnterprise = enterpriseService.getAllEnterprise();
         return Response.status(Response.Status.OK).entity(existingEnterprise).build();
    }

    @GET
    @Path("/{idEnterprise}")
    public Response getEnterpriseById(@PathParam("idEnterprise") Long idEnterprise) throws NotFoundException {
        Enterprise existingEnterprise = enterpriseService.getEnterpriseById(idEnterprise);
        return Response.status(Response.Status.OK).entity(existingEnterprise).build();
    }

    @POST
    @Transactional
    public Response createEnterprise(Enterprise enterprise) throws BusinessException {
        enterpriseService.createEnterprise(enterprise);
        return Response.status(Response.Status.CREATED).entity(enterprise).build();
    }

    @PUT
    @Path("/{idEnterprise}")
    @Transactional
    public Response updateEnterprise(@PathParam("idEnterprise") Long idEnterprise, Enterprise enterprise) throws NotFoundException {
       Enterprise existingEnterprise = enterpriseService.updateEnterprise(idEnterprise, enterprise);
       return Response.status(Response.Status.OK).entity(existingEnterprise).build();
    }

    @DELETE
    @Path("/{idEnterprise}")
    @Transactional
    public Response deleteEnterprise(@PathParam("idEnterprise") Long idEnterprise) throws BusinessException {
        enterpriseService.deleteEnterprise(idEnterprise);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @Path("/{idEnterprise}/addEstablishment")
    @Transactional
    public Response addEstablishment(@PathParam("idEnterprise") Long idEnterprise, Establishment establishment) throws BusinessException {
        Enterprise existingEnterprise = enterpriseService.addEstablishment(idEnterprise, establishment);
        return Response.status(Response.Status.OK).entity(existingEnterprise).build();
    }

    @DELETE
    @Path("/{idEnterprise}/removeEstablishment")
    public Response removeEstablishment(@PathParam("idEnterprise") Long idEnterprise, Establishment establishment) throws BusinessException {
        Enterprise existingEnterprise = enterpriseService.removeEstablishment(idEnterprise, establishment);
        return Response.status(Response.Status.OK).entity(existingEnterprise).build();
    }

}