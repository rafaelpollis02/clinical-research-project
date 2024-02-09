package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.*;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.repository.EstablishmentRepository;
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

    @Inject
    EstablishmentRepository establishmentRepository;

    @Inject
    AutenticateService autenticateService;

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
            Autenticate autenticate = new Autenticate();
            autenticate.setCpf(person.getCpf());
            autenticate.setEmail(person.getEmail());
            autenticateService.saveAutenticate(autenticate);
      }
        return person;
    }

    public Person addPersonType(Long idPerson, PersonType personType) throws BusinessException {

        Person existingPerson = personRepository.findById(idPerson);
        PersonType existingPersonType = personTypeRepository.findById(idPerson);
        Long personTypeId = personType.getId();

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

    public Person addEstablishment(Long idPerson, Establishment establishment) throws BusinessException {

        Person existingPerson = personRepository.findById(idPerson);
        Establishment existingEstablishment = establishmentRepository.findById(idPerson);
        Long idEstablishment = establishment.getId();

        if (existingPerson == null) {
            throw new BusinessException("Person not registered with the ID " + idPerson);
        }
        if (existingEstablishment == null) {
            throw new BusinessException("Establishment not registered with the ID " + idEstablishment);
        } else {
            existingPerson.getEstablishment().add(establishment);
            personRepository.persist(existingPerson);
        }
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
            personRepository.persist(existingPerson);
        }
        return existingPerson;
    }

    public void deletePerson(Long idPerson) {
        Person existingPerson = personRepository.findById(idPerson);
        if (existingPerson != null) {
            personRepository.delete(existingPerson);
        }
    }

}
