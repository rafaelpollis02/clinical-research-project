package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.Establishment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EstablishmentRepository implements PanacheRepository<Establishment> {
}
