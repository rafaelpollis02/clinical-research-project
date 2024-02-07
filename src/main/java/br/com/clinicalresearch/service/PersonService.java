package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Person;
import br.com.clinicalresearch.domain.PersonType;
import br.com.clinicalresearch.repository.PersonRepository;
import br.com.clinicalresearch.repository.PersonTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class PersonService {

    @Inject
    PersonRepository personRepository;

    @Inject
    PersonTypeRepository personTypeRepository;

    public List<Person> getAllPerson() {
        return personRepository.listAll();
    }

    public Person savePerson(Person person) {
        personRepository.persist(person);
        return person;
    }

}
