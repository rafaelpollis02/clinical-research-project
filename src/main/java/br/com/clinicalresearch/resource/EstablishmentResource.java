package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.service.EstablishmentService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@ApplicationScoped
@Path("api/v1/establishment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstablishmentResource {

    @Inject
    EstablishmentService establishmentService;

    @GET
    public List<Establishment> getAllEstablishment() {
        return establishmentService.getAllEstablishment();
    }

    @GET
    @Path("/{idEstablishment}")
    public Establishment getEstablishmentById(@PathParam("idEstablishment") Long idEstablishment) throws BusinessException {
        return establishmentService.getEstablishmentById(idEstablishment);
    }

    @POST
    @Transactional
    public Establishment saveEstablishment(Establishment establishment) {
        establishmentService.saveEstablishment(establishment);
        return establishment;
    }

    @PUT
    @Path("/{idEstablishment}")
    @Transactional
    public Establishment updateEstablishment(@PathParam("idEstablishment") Long idEstablishment, Establishment establishment) throws BusinessException {
        return establishmentService.updateEstablishment(idEstablishment, establishment);
    }

    @DELETE
    @Path("/{idEstablishment}")
    @Transactional
    public void deleteEstablishment(@PathParam("idEstablishment") Long idEstablishment) throws BusinessException {
        establishmentService.deleteEstablishment(idEstablishment);
    }

}
