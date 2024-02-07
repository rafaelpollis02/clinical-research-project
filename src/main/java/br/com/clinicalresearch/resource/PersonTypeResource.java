package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.PersonType;
import br.com.clinicalresearch.service.PersonTypeService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("api/v1/persontype")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonTypeResource {

    @Inject
    PersonTypeService personTypeService;

    @GET
    public List<PersonType> getAllPersonType() {
        return personTypeService.getAllPersonType();
    }

    @POST
    @Transactional
    public PersonType savePersonType(PersonType personType) {
        personTypeService.savePersonType(personType);
        return personType;
    }


}
