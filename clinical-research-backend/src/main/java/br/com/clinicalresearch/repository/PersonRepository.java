package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {
    public Person findPersonByCpf(String cpf) {
        return find("cpf", cpf).firstResult();
    }

}
