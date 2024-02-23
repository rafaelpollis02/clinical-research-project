package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.EnterpriseEstablishment;
import br.com.clinicalresearch.dto.EnterpriseEstablishmentRequest;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NotFoundException;
import br.com.clinicalresearch.service.EnterpriseEstablishmentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("api/v1/enterpriseEstablishment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnterpriseEstablishmentResource {

    @Inject
    EnterpriseEstablishmentService enterpriseEstablishmentService;

    @GET
    public Response getAllEnterpriseEstablishment() {
        List<EnterpriseEstablishment> existingEnterpriseEstablishment = enterpriseEstablishmentService.getAllEnterpriseEstablishment();
        return Response.status(Response.Status.OK).entity(existingEnterpriseEstablishment).build();
    }

    @GET
    @Path("/{idEnterpriseEstablishment}")
    public Response getEstablishmentById(@PathParam("idEnterpriseEstablishment") Long idEnterpriseEstablishment) throws BusinessException {
        EnterpriseEstablishment existingEnterpriseEstablishment = enterpriseEstablishmentService.getEnterpriseEstablishmentById(idEnterpriseEstablishment);
        return Response.status(Response.Status.OK).entity(existingEnterpriseEstablishment).build();
    }

    @PUT
    @Path("/{idEnterpriseEstablishment}")
    @Transactional
    public Response updateEnterpriseEstablishment(@PathParam("idEnterpriseEstablishment") Long idEnterpriseEstablishment, EnterpriseEstablishment enterpriseEstablishment) throws NotFoundException {
        enterpriseEstablishmentService.updateEnterpriseEstablishment(idEnterpriseEstablishment, enterpriseEstablishment);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Transactional
    public Response addEnterpriseEstablishment(EnterpriseEstablishmentRequest enterpriseEstablishmentRequest) throws BusinessException, NotFoundException {
        EnterpriseEstablishment existingEnterpriseEstablishment = enterpriseEstablishmentService.addEnterpriseEstablishment(enterpriseEstablishmentRequest);
        return Response.status(Response.Status.CREATED).entity(existingEnterpriseEstablishment).build();
    }

    @DELETE
    @Path("/{idEnterpriseEstablishment}")
    @Transactional
    public Response removeEnterpriseEstablishment(@PathParam("idEnterpriseEstablishment") Long idEnterpriseEstablishment) throws NotFoundException {
        enterpriseEstablishmentService.removeEnterpriseEstablishment(idEnterpriseEstablishment);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
