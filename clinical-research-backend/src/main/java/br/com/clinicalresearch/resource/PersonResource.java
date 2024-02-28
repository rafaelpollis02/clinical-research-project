package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.Person;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NoContentException;
import br.com.clinicalresearch.service.PersonService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("api/v1/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    PersonService personService;

    @GET
    public List<Person> getAllPerson() throws NoContentException {
        return personService.getAllPerson();
    }

    @GET
    @Path("/{idPerson}/id")
    public Person getPersonById(@PathParam("idPerson") Long idPerson) throws NoContentException {
        return personService.getPersonById(idPerson);
    }

    @GET
    @Path("/{fullName}/fullName")
    public List<Person> getPersonByfullName(@PathParam("fullName") String fullName) throws NoContentException {
        return personService.getPersonByfullName(fullName);
    }

    @GET
    @Path("/{cpfOrEmail}/cpfOrEmail")
    public Person getPersonByCpfOrEmail(@PathParam("cpfOrEmail") String cpfOrEmail) throws NoContentException {
        return personService.getPersonByCpfOrEmail(cpfOrEmail);
    }

    @GET
    @Path("/{cpf}/cpf")
    public Person getPersonByCpf(@PathParam("cpf") String cpf) throws NoContentException {
        return personService.getPersonByCpf(cpf);
    }

    @GET
    @Path("/{email}/email")
    public Person getPersonByEmail(@PathParam("email") String email) throws NoContentException {
        return personService.getPersonByEmail(email);
    }

    @POST
    @Transactional
    public Response createPerson(@Valid Person person) throws BusinessException, NotFoundException {
        personService.createPerson(person);
        return Response.status(Response.Status.CREATED).entity(person).build();
    }

    @PUT
    @Path("/{idPerson}/id")
    @Transactional
    public Person updatePerson(@PathParam("idPerson") Long idPerson, @Valid Person person) throws NoContentException {
        return personService.updatePerson(idPerson, person);
    }

    @DELETE
    @Path("/{idPerson}/id")
    @Transactional
    public void deletePerson(@PathParam("idPerson") Long idPerson) throws NoContentException {
        personService.deletePerson(idPerson);
    }
}
