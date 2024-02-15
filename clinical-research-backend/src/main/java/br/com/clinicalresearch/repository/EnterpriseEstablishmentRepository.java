package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.relational.EnterpriseEstablishment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnterpriseEstablishmentRepository implements PanacheRepository<EnterpriseEstablishment> {

}
