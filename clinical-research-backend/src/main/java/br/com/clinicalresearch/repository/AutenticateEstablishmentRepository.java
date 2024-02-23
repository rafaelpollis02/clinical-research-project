package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.AutenticateEstablishment;
import br.com.clinicalresearch.domain.EnterpriseEstablishment;
import br.com.clinicalresearch.domain.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AutenticateEstablishmentRepository implements PanacheRepository<AutenticateEstablishment> {

    public AutenticateEstablishment findByIdAutenticateAndIdEstablishmento(Long idAutenticate, Long idEstablishment) {
        return find("autenticate.id = ?1 and establishment.id = ?2", idAutenticate, idEstablishment).firstResult();
    }

}
