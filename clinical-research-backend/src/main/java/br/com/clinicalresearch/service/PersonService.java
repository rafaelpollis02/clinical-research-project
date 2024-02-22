package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Autenticate;
import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.domain.Person;
import br.com.clinicalresearch.domain.PersonType;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NotFoundException;
import br.com.clinicalresearch.repository.EstablishmentRepository;
import br.com.clinicalresearch.repository.PersonRepository;
import br.com.clinicalresearch.repository.PersonTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PersonService {
    @Inject
    PersonRepository personRepository;
    @Inject
    PersonTypeRepository personTypeRepository;
    @Inject
    EstablishmentRepository establishmentRepository;
    @Inject
    AutenticateService autenticateService;

    public List<Person> getAllPerson() {
        return personRepository.listAll();
    }

    public Person getPersonById(Long idPerson) throws NotFoundException {
        Person existingPerson = personRepository.findById(idPerson);
        if (existingPerson == null) {
            throw new NotFoundException("Person not registered with the ID " + idPerson);
        }
        return existingPerson;
    }

    public Person getPersonByCpf(String cpf) throws NotFoundException {
        Person existingPerson = personRepository.findPersonByCpf(cpf);
        if (existingPerson == null) {
            throw new NotFoundException("Person not registered with the CPF " + cpf);
        }
        return existingPerson;
    }

    public Person createPerson(Person person) throws BusinessException {
        Person existingPerson = personRepository.findPersonByCpf(person.getCpf());
        if (existingPerson != null) {
            throw new BusinessException("Person duplicate by cpf " + person.getCpf());
        } else {
            personRepository.persist(person);
            Autenticate autenticate = new Autenticate();
            autenticate.setCpf(person.getCpf());
            autenticate.setEmail(person.getEmail());
            autenticateService.createAutenticate(autenticate, person);
        }
        return person;
    }

    public Person addPersonType(Long idPerson, PersonType personType) throws BusinessException {

        Person existingPerson = personRepository.findById(idPerson);
        Long personTypeId = personType.getId();
        PersonType existingPersonType = personTypeRepository.findById(personTypeId);

        if (existingPerson == null) {
            throw new BusinessException("Person not registered with the ID " + personTypeId);
        }
        if (existingPersonType == null) {
            throw new BusinessException("PersonType not registered with the ID " + idPerson);
        } else {
            existingPerson.getPersonType().add(personType);
            personRepository.persist(existingPerson);
        }
        return existingPerson;
    }

    public Person removePersonType(Long idPerson, PersonType personType) throws BusinessException {

        Person existingPerson = personRepository.findById(idPerson);
        Long idPersonType = personType.getId();

        if (existingPerson == null) {
            throw new BusinessException("Person not registered with the ID " + idPersonType);
        }

        if (!existingPerson.getPersonType().removeIf(estab -> estab.getId().equals(idPersonType))) {
            throw new BusinessException("PersonType with ID " + idPersonType + " is not associated with the Person.");
        }

        personRepository.persist(existingPerson);
        return existingPerson;
    }

    public Person updatePerson(Long idPerson, Person person) throws BusinessException {
        Person existingPerson = personRepository.findById(idPerson);
        if (existingPerson == null) {
            throw new BusinessException("Person not registered with the ID " + idPerson);
        } else {
            existingPerson.setFullName(person.getFullName());
            existingPerson.setCpf(person.getCpf());
            existingPerson.setRg(person.getRg());
            existingPerson.setPhoneNumber(person.getPhoneNumber());
            existingPerson.setEmail(person.getEmail());
            existingPerson.setBirthDate(person.getBirthDate());
            existingPerson.setUpdateDate(LocalDateTime.now());
            personRepository.persist(existingPerson);
        }
        return existingPerson;
    }

    public void deletePerson(Long idPerson) throws BusinessException {
        Person existingPerson = personRepository.findById(idPerson);
        if (existingPerson == null) {
            throw new BusinessException("Person not registered with the ID " + idPerson);
        } else {
            personRepository.delete(existingPerson);
        }
    }

}
