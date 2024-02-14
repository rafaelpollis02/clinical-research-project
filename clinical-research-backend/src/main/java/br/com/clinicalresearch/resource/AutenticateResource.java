package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.Autenticate;
import br.com.clinicalresearch.dto.AutenticateRequest;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.service.AutenticateService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("api/v1/autenticate")
public class AutenticateResource {

    @Inject
    AutenticateService autenticateService;

    @POST
    public void getAutenticateByCpfOrEmail (AutenticateRequest autenticateRequest) throws BusinessException {
        autenticateService.getAutenticateByCpfOrEmail(autenticateRequest);
    }

    @PUT
    @Path("/{idAutenticate}")
    @Transactional
    public Autenticate updatePasswordAutenticate(@PathParam("idAutenticate") Long idAutenticate, Autenticate autenticate){
       return autenticateService.updatePasswordAutenticate(idAutenticate, autenticate);
    }

}
