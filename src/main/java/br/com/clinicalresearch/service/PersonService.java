package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Person;
import br.com.clinicalresearch.domain.PersonType;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.repository.PersonRepository;
import br.com.clinicalresearch.repository.PersonTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PersonService {

    @Inject
    PersonRepository personRepository;

    @Inject
    PersonTypeRepository personTypeRepository;

    public List<Person> getAllPerson() {
        return personRepository.listAll();
    }

    public Person getPersonById(Long idPerson) throws BusinessException {
        Person existingPerson = personRepository.findById(idPerson);
        if (existingPerson == null) {
            throw new BusinessException("Person not registered with the ID " + idPerson);
        }
        return existingPerson;
    }

    public Person savePerson(Person person) throws BusinessException {
        Optional<Person> existingPerson = personRepository.findPersonByCpf(person.getCpf());
        if (existingPerson.isPresent()) {
            throw new BusinessException("Person duplicate by cpf " + person.getCpf());
        } else {
            personRepository.persist(person);
        }
        return person;
    }

    public Person updatePerson(Long id, Person person) throws BusinessException {
        Person existingPerson = personRepository.findById(id);
        if (existingPerson == null) {
            throw new BusinessException("Person not registered with the ID " + id);
        } else {
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

    public Person addPersonType(Long personId, PersonType personType) throws BusinessException {

        Person existingPerson = personRepository.findById(personId);
        PersonType existingPersonType = personTypeRepository.findById(personId);
        Long personTypeId = personType.getId();

        if (existingPerson == null) {
            throw new BusinessException("Person not registered with the ID " + personTypeId);
        }
        if (existingPersonType == null) {
            throw new BusinessException("PersonType not registered with the ID " + personId);
        } else {
            existingPerson.getPersonTypes().add(personType);
            personRepository.persist(existingPerson);
        }
        return existingPerson;
    }

}
