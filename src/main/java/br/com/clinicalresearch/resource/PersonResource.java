package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.Person;
import br.com.clinicalresearch.service.PersonService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("api/v1/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    PersonService personService;

    @GET
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }

    @POST
    @Transactional
    public Person savePerson(Person person) {
        personService.savePerson(person);
        return person;
    }

}
