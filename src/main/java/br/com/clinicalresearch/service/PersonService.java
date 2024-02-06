package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Person;
import br.com.clinicalresearch.repository.PersonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PersonService {

    @Inject
    PersonRepository personRepository;

    public List<Person> getAllPerson() {
        return personRepository.listAll();
    }

    public Person savePerson(Person person){
        personRepository.persist(person);
        return person;
    }

}
