package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {
}
