package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.PersonType;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NoContentException;
import br.com.clinicalresearch.repository.PersonTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PersonTypeService {

    @Inject
    PersonTypeRepository personTypeRepository;

    public List<PersonType> getAllPersonType() throws NoContentException {
        List<PersonType> existingPersonType = personTypeRepository.listAll();

        if (existingPersonType.isEmpty()) {
            throw new NoContentException();
        } else {
            return existingPersonType;
        }
    }

    public PersonType getPersonTypeById(Long idPersonType) throws NoContentException {
        PersonType existingPersonType = personTypeRepository.findById(idPersonType);
        if (existingPersonType == null) {
            throw new NoContentException();
        } else {
            return existingPersonType;
        }
    }

    public PersonType createPersonType(PersonType personType) throws BusinessException {
        PersonType existingPersonType = personTypeRepository.findByType(personType.getType());
        if (existingPersonType != null) {
            throw new BusinessException("Já existe um persontype com este tipo " + personType.getType());
        } else {
            personTypeRepository.persist(personType);
        }
        return personType;
    }

    public PersonType updatePersonTasy(Long idPersonType, PersonType personType) {
        PersonType existingPersonType = personTypeRepository.findById(idPersonType);
        if (existingPersonType != null) {
            existingPersonType.setType(personType.getType());
            existingPersonType.setStatus(personType.getStatus());
            existingPersonType.setUpdateDate(LocalDateTime.now());
            personTypeRepository.persist(existingPersonType);
        }
        return existingPersonType;
    }

    public void deletePersonType(Long idPersonType) {
        PersonType existingPersonType = personTypeRepository.findById(idPersonType);
        if (existingPersonType != null) {
            personTypeRepository.delete(existingPersonType);
        }
    }
}
