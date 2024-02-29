package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.*;
import br.com.clinicalresearch.dto.PersonPersonTypeRequest;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NoContentException;
import br.com.clinicalresearch.repository.PersonPersonTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PersonPersonTypeService {


    @Inject
    PersonPersonTypeRepository personPersonTypeRepository;

    @Inject
    PersonService personService;

    @Inject
    PersonTypeService personTypeService;

    public List<PersonPersonType> getAllPersonPersonType() throws NoContentException {
        List<PersonPersonType> existingPersonPersonType = personPersonTypeRepository.listAll();

        if (existingPersonPersonType.isEmpty()){
            throw new NoContentException();
        }else{
            return existingPersonPersonType;
        }
    }

    public PersonPersonType getPersonPersonTypeById(Long idPersonPersonType) throws NoContentException {
        PersonPersonType existingPersonPersonType = personPersonTypeRepository.findById(idPersonPersonType);

        if (existingPersonPersonType == null) {
            throw new NoContentException();
        }
        return existingPersonPersonType;
    }

    public PersonPersonType updatePersonPersonType(Long idPersonPersonType, PersonPersonType personPersonType) throws NoContentException {
        PersonPersonType existingPersonPersonType = personPersonTypeRepository.findById(idPersonPersonType);

        if (existingPersonPersonType == null) {
            throw new NoContentException();
        } else {
            personPersonType.setStatus(personPersonType.getStatus());
            personPersonTypeRepository.persist(existingPersonPersonType);
        }
        return existingPersonPersonType;
    }


    public PersonPersonType addPersonPersonType(PersonPersonTypeRequest personPersonTypeRequest) throws NoContentException, BusinessException {

        Long idPerson = personPersonTypeRequest.idPerson();
        Long idPersonType = personPersonTypeRequest.idPersonType();

        Person person = personService.getPersonById(idPerson);
        if (person == null) {
            throw new NoContentException();
        }

        PersonType personType = personTypeService.getPersonTypeById(idPersonType);
        if (personType == null) {
            throw new NoContentException();
        }

        PersonPersonType existingRelationship = personPersonTypeRepository.findByIdPersonAndIdPersonType(idPerson, idPersonType);
        if (existingRelationship != null) {
            throw new BusinessException("Já existe uma relação entre esta pessoa e o tipo de pessoa");
        } else {
            PersonPersonType personPersonType = new PersonPersonType();
            personPersonType.setPerson(person);
            personPersonType.setPersonType(personType);
            personPersonTypeRepository.persist(personPersonType);
            return personPersonType;
        }
    }

    @Transactional
    public void removePersonPersonType(Long idPersonPersonType) throws NoContentException {

        PersonPersonType existingPersonPersonType = personPersonTypeRepository.findById(idPersonPersonType);

        if (existingPersonPersonType == null) {
            throw new NoContentException();
        } else {
            personPersonTypeRepository.delete(existingPersonPersonType);
        }
    }
}
