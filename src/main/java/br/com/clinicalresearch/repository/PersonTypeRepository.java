package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.PersonType;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonTypeRepository implements PanacheRepository<PersonType> {
}
