package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {
    public Person findPersonByCpf(String cpf) {
        return find("cpf", cpf).firstResult();
    }

    public Person findPersonByEmail(String email) {
        return find("email", email).firstResult();
    }

    public Person findPersonByFullName(String fullName) {
        return find("fullName", fullName).firstResult();
    }

}
