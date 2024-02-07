package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Person;
import br.com.clinicalresearch.domain.PersonType;
import br.com.clinicalresearch.repository.PersonRepository;
import br.com.clinicalresearch.repository.PersonTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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

    public Person updatePerson(Long id, Person person) {
        Person existingPerson = personRepository.findById(id);
        if (existingPerson != null) {
            existingPerson.setFullName(person.getFullName());
            existingPerson.setCpf(person.getCpf());
            existingPerson.setRg(person.getRg());
            existingPerson.setPhoneNumber(person.getPhoneNumber());
            existingPerson.setEmail(person.getEmail());
            existingPerson.setBirthDate(person.getBirthDate());
            personRepository.persist(existingPerson);
        }
        return existingPerson;
    }

    public void deletePerson(Long id) {
        Person existingPerson = personRepository.findById(id);
        if (existingPerson != null) {
            personRepository.delete(existingPerson);
        }
    }

    public Person addPersonType(Long personId, PersonType personType) {
        Person person = personRepository.findById(personId);
        Long personTypeId = personType.getId();
        if (person != null && personType != null) {
            person.getPersonTypes().add(personType);
            personRepository.persist(person);
        }
        return person;
    }

}
