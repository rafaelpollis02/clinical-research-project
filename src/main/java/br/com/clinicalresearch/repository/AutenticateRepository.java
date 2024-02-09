package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.Autenticate;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AutenticateRepository implements PanacheRepository<Autenticate> {
}
