package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.PersonType;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class PersonTypeRepository implements PanacheRepository<PersonType> {

    public PersonType findByType(String type) {
        return find("type", type).firstResult();
    }

}
