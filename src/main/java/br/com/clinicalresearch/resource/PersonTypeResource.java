package br.com.clinicalresearch.resource;

import br.com.clinicalresearch.domain.PersonType;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.service.PersonTypeService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;

@Path("api/v1/persontype")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonTypeResource {

    @Inject
    PersonTypeService personTypeService;

    @GET
    @Operation(summary = "Obter todos os tipos de pessoa", description = "Retorna uma lista com todos os tipos de pessoa")
    public List<PersonType> getAllPersonType() {
        return personTypeService.getAllPersonType();
    }

    @GET
    @Path("/{idPersonType}")
    @Operation(summary = "Obter tipo de pessoa específica", description = "Retorna um tipo de pessoa específico passando o ID")
    public PersonType getPersonTypeById(@PathParam("idPersonType") Long idPersonType) throws BusinessException {
       return personTypeService.getPersonTypeById(idPersonType);
    }

    @POST
    @Transactional
    @Operation(summary = "Salvar um tipo de pessoa", description = "Salva um tipo de pessoa no banco de dados")
    public Response savePersonType(@Valid PersonType personType) throws BusinessException {
        personTypeService.savePersonType(personType);
        return Response.status(Response.Status.CREATED).entity(personType).build();
    }

    @PUT
    @Transactional
    @Path("/{idPersonType}")
    @Operation(summary = "Atualizar um tipo de pessoa", description = "Atualiza dados de um tipo de pessoa específica")
    public PersonType updatePersonType(@PathParam("idPersonType") Long idPersonType, @Valid PersonType personType) {
        return personTypeService.updatePersonTasy(idPersonType, personType);
    }

    @DELETE
    @Transactional
    @Path("/{idPersonType}")
    @Operation(summary = "Remover um tipo de pessoa", description = "Remove um tipo de pessoa específica do banco de dados")
    public void deletePersonType(@PathParam("idPersonType") Long idPersonType) {
        personTypeService.deletePersonType(idPersonType);
    }
}
