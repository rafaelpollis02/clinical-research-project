package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.Enterprise;
import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.domain.Person;
import br.com.clinicalresearch.domain.PersonType;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.service.PersonService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;

@Path("api/v1/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    PersonService personService;

    @GET
    @Operation(summary = "Obter todas as pessoas", description = "Retorna uma lista de todas as pessoas registradas")
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }

    @GET
    @Path("/{idPerson}")
    @Operation(summary = "Obter uma pessoa específica", description = "Retorna uma pessoa específica passando o ID")
    public Person getPersonById(@PathParam("idPerson") Long idPerson) throws BusinessException {
        return personService.getPersonById(idPerson);
    }

    @POST
    @Transactional
    @Operation(summary = "Salvar uma pessoa", description = "Salva uma pessoa no banco de dados")
    public Response savePerson(@Valid Person person) throws BusinessException {
        personService.savePerson(person);
        return Response.status(Response.Status.CREATED).entity(person).build();
    }

    @POST
    @Path("/{idPerson}/addPersonType")
    @Transactional
    @Operation(summary = "Adicionar um tipo de pessoa na pessoa", description = "Adicionar um tipo de pessoa em uma pessoa específica")
    public Person addPersonType(@PathParam("idPerson") Long idPerson, PersonType personType) throws BusinessException {
        return personService.addPersonType(idPerson, personType);
    }

    @DELETE
    @Path("/{idPerson}/removePersonType")
    @Transactional
    public Person removePersonType(@PathParam("idPerson") Long idPerson, PersonType personType) throws BusinessException {
        return personService.removePersonType(idPerson, personType);
    }


    @POST
    @Path("/{idPerson}/addEstablishment")
    @Transactional
    @Operation(summary = "Adicionar um estabelecimento na pessoa", description = "Adicionar um estabelecimento em uma pessoa específica")
    public Person addEstablishment(@PathParam("idPerson") Long idPerson, Establishment establishment) throws BusinessException {
        return personService.addEstablishment(idPerson, establishment);
    }

    @DELETE
    @Path("/{idPerson}/removeEstablishment")
    @Transactional
    public Person removeEstablishment(@PathParam("idPerson") Long idPerson, Establishment establishment) throws BusinessException {
        return personService.removeEstablishment(idPerson, establishment);
    }

    @PUT
    @Path("/{idPerson}")
    @Transactional
    @Operation(summary = "Atualizar uma pessoa", description = "Atualiza dados de uma pessoa específica")
    public Person updatePerson(@PathParam("idPerson") Long idPerson, @Valid Person person) throws BusinessException {
        return personService.updatePerson(idPerson, person);
    }

    @DELETE
    @Path("/{idPerson}")
    @Transactional
    @Operation(summary = "Remover uma pessoa", description = "Remove uma pessoa específica do banco de dados")
    public void deletePerson(@PathParam("idPerson") Long idPerson) throws BusinessException {
        personService.deletePerson(idPerson);
    }

}
