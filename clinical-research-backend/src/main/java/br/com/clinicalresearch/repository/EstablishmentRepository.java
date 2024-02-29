package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.domain.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EstablishmentRepository implements PanacheRepository<Establishment> {

    public List<Establishment> findByName(String name) {
        return list("lower(name) LIKE ?1", "%" + name.toLowerCase() + "%");
    }

}
