package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.service.EstablishmentService;
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

    @POST
    @Transactional
    public Establishment saveEstablishment(Establishment establishment) {
        establishmentService.saveEstablishment(establishment);
        return establishment;
    }

}
