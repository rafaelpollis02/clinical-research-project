package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.PersonType;
import br.com.clinicalresearch.repository.PersonTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PersonTypeService {

    @Inject
    PersonTypeRepository personTypeRepository;

    public List<PersonType> getAllPersonType() {
        return personTypeRepository.listAll();
    }

    public PersonType savePersonType(PersonType personType) {
        personTypeRepository.persist(personType);
        return personType;
    }


}
