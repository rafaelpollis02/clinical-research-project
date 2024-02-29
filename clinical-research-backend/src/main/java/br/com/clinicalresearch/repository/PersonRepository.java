package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {
    public Person findByCpf(String cpf) {
        return find("cpf", cpf).firstResult();
    }

    public Person findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public List<Person> findByFullName(String fullName) {
        return list("lower(fullName) LIKE ?1", "%" + fullName.toLowerCase() + "%");
    }

}
