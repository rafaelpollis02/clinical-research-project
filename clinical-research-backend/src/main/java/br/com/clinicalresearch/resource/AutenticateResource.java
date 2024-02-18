package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.Autenticate;
import br.com.clinicalresearch.dto.AutenticateRequest;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.InvalidLoginException;
import br.com.clinicalresearch.exceptions.NotFoundException;
import br.com.clinicalresearch.service.AutenticateService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("api/v1/autenticate")
public class AutenticateResource {

    @Inject
    AutenticateService autenticateService;

    @GET
    public Response getAutenticateByCpfOrEmail(AutenticateRequest autenticateRequest) throws NotFoundException {
        autenticateService.getAutenticateByCpfOrEmail(autenticateRequest);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    public Response getAccessByCpfOrEmail(AutenticateRequest autenticateRequest) throws InvalidLoginException {
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


    @PUT
    @Transactional
    public Autenticate updatePasswordAutenticate(AutenticateRequest autenticateRequest) throws NotFoundException {
       return autenticateService.updatePasswordAutenticate(autenticateRequest);
    }

}
