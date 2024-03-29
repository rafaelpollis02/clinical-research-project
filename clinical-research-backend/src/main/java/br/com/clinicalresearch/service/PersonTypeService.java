package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.PersonType;
import br.com.clinicalresearch.exceptions.BusinessException;
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

    public List<PersonType> getAllPersonType() {
        return personTypeRepository.listAll();
    }

    public PersonType getPersonTypeById(Long idPersonType) throws BusinessException {
        PersonType existingPersonType = personTypeRepository.findById(idPersonType);
        if (existingPersonType == null) {
            throw new BusinessException("PersonType not registered with the ID " + idPersonType);
        } else {
            return existingPersonType;
        }
    }

    public PersonType savePersonType(PersonType personType) throws BusinessException {
        Optional<PersonType> existingPersonType = personTypeRepository.findPersonTypeByType(personType.getType());
        if (existingPersonType.isPresent()) {
            throw new BusinessException("PersonType duplicate by type " + personType.getType());
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
