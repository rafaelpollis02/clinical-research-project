package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.Enterprise;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NoContentException;
import br.com.clinicalresearch.service.EnterpriseService;
import jakarta.inject.Inject;
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
    public Response getAllEnterprise() throws NoContentException {
        List<Enterprise> existingEnterprise = enterpriseService.getAllEnterprise();
        return Response.status(Response.Status.OK).entity(existingEnterprise).build();
    }

    @GET
    @Path("/{idEnterprise}/id")
    public Response getEnterpriseById(@PathParam("idEnterprise") Long idEnterprise) throws NoContentException {
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
    @Path("/{idEnterprise}/id")
    @Transactional
    public Response updateEnterprise(@PathParam("idEnterprise") Long idEnterprise, Enterprise enterprise) throws NoContentException {
        Enterprise existingEnterprise = enterpriseService.updateEnterprise(idEnterprise, enterprise);
        return Response.status(Response.Status.OK).entity(existingEnterprise).build();
    }

    @GET
    @Path("/{name}/name")
    public Response getEnterpriseByName(@PathParam("name") String name) throws NoContentException {
        List<Enterprise> existingEnterprise = enterpriseService.getEnterpriseByName(name);
        return Response.status(Response.Status.OK).entity(existingEnterprise).build();
    }

    @DELETE
    @Path("/{idEnterprise}/id")
    @Transactional
    public Response deleteEnterprise(@PathParam("idEnterprise") Long idEnterprise) throws NoContentException {
        enterpriseService.deleteEnterprise(idEnterprise);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}