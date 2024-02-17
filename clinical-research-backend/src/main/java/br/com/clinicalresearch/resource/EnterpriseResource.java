package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.Enterprise;
import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.service.EnterpriseService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("api/v1/enterprise")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnterpriseResource {

    @Inject
    EnterpriseService enterpriseService;

    @GET
    public List<Enterprise> getAllEnterprise() {
        return enterpriseService.getAllEnterprise();
    }

    @GET
    @Path("/{idEnterprise}")
    public Enterprise getEnterpriseById(@PathParam("idEnterprise") Long idEnterprise) throws BusinessException {
        return enterpriseService.getEnterpriseById(idEnterprise);
    }

    @POST
    @Transactional
    public Enterprise saveEnterprise(Enterprise enterprise) throws BusinessException {
        enterpriseService.saveEnterprise(enterprise);
        return enterprise;
    }

    @POST
    @Path("/{idEnterprise}/addEstablishment")
    @Transactional
    public Enterprise addEstablishment(@PathParam("idEnterprise") Long idEnterprise, Establishment establishment) throws BusinessException {
        return enterpriseService.addEstablishment(idEnterprise, establishment);
    }

    @DELETE
    @Path("/{idEnterprise}/removeEstablishment")
    @Transactional
    public Enterprise removeEstablishment(@PathParam("idEnterprise") Long idEnterprise, Establishment establishment) throws BusinessException {
        return enterpriseService.removeEstablishment(idEnterprise, establishment);
    }


    @PUT
    @Path("/{idEnterprise}")
    @Transactional
    public Enterprise updateEnterprise(@PathParam("idEnterprise") Long idEnterprise, Enterprise enterprise) throws BusinessException {
        return enterpriseService.updateEnterprise(idEnterprise, enterprise);
    }

    @DELETE
    @Path("/{idEnterprise}")
    @Transactional
    public void deleteEnterprise(@PathParam("idEnterprise") Long idEnterprise) throws BusinessException {
        enterpriseService.deleteEnterprise(idEnterprise);
    }

}