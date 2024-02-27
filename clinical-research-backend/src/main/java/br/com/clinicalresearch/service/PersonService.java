package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Autenticate;
import br.com.clinicalresearch.domain.Person;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NoContentException;
import br.com.clinicalresearch.integration.WebServiceZipCode;
import br.com.clinicalresearch.repository.PersonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PersonService {
    @Inject
    PersonRepository personRepository;

    @Inject
    AutenticateService autenticateService;

    @Inject
    @RestClient
    WebServiceZipCode webServiceZipCode;

    public List<Person> getAllPerson() throws NoContentException {
        List<Person> existingPerson = personRepository.listAll();

        if (existingPerson.isEmpty()) {
            throw new NoContentException();
        } else {
            return existingPerson;
        }
    }

    public Person getPersonById(Long idPerson) throws NoContentException {
        Person existingPerson = personRepository.findById(idPerson);
        if (existingPerson == null) {
            throw new NoContentException();
        }
        return existingPerson;
    }

    public List<Person> getPersonByfullName(String fullName) throws NoContentException {
        List<Person> existingPerson = personRepository.findByFullName(fullName);

        if (existingPerson.isEmpty()) {
            throw new NoContentException();
        } else {
            return existingPerson;
        }
    }

    public Person getPersonByCpfOrEmail(String cpfOrEmail) throws NoContentException {
        Person existingPersonCpf = personRepository.findByCpf(cpfOrEmail);
        Person existingPersonEmail = personRepository.findByEmail(cpfOrEmail);

        if (existingPersonCpf != null) {
            return existingPersonCpf;
        } else if (existingPersonEmail != null) {
            return existingPersonEmail;
        } else {
            throw new NoContentException();
        }
    }

    public Person getPersonByCpf(String cpf) throws NoContentException {
        Person existingPerson = personRepository.findByCpf(cpf);

        if (existingPerson == null) {
            throw new NoContentException();
        }
        return existingPerson;
    }

    public Person getPersonByEmail(String email) throws NoContentException {
        Person existingPerson = personRepository.findByEmail(email);

        if (existingPerson == null) {
            throw new NoContentException();
        }
        return existingPerson;
    }

    public Person createPerson(Person person) throws BusinessException {
        Person existingPerson = personRepository.findByCpf(person.getCpf());
        if (existingPerson != null) {
            throw new BusinessException("Person duplicate by cpf " + person.getCpf());
        } else {

            if (person.getZipCode() != null || person.getZipCode() != "") {
                getZipCodeWebService(person);
            }

            personRepository.persist(person);
            Autenticate autenticate = new Autenticate();
            autenticate.setCpf(person.getCpf());
            autenticate.setEmail(person.getEmail());
            autenticateService.createAutenticate(autenticate, person);
        }
        return person;
    }

    public Person getZipCodeWebService(Person person) {

        Response webService = webServiceZipCode.getZipCodeByWebService(person.getZipCode());
        JsonObject webServiceInfo = webService.readEntity(JsonObject.class);

        person.setStreet(webServiceInfo.getString("logradouro"));
        person.setNeighborhood(webServiceInfo.getString("bairro"));
        person.setCity(webServiceInfo.getString("localidade"));
        person.setState(webServiceInfo.getString("uf"));
        return person;
    }


    public Person updatePerson(Long idPerson, Person person) throws NoContentException {
        Person existingPerson = personRepository.findById(idPerson);
        if (existingPerson == null) {
            throw new NoContentException();
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

    public void deletePerson(Long idPerson) throws NoContentException {
        Person existingPerson = personRepository.findById(idPerson);
        if (existingPerson == null) {
            throw new NoContentException();
        } else {
            personRepository.delete(existingPerson);
        }
    }

}
