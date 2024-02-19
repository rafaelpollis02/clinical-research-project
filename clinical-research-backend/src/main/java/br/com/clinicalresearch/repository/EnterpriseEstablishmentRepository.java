package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.EnterpriseEstablishment;
import br.com.clinicalresearch.domain.Establishment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class EnterpriseEstablishmentRepository implements PanacheRepository<EnterpriseEstablishment> {

    public EnterpriseEstablishment findByIdEnterpriseAndIdEstabelecimento(Long idEnterprise, Long idEstabelecimento) {
        return find("enterprise.id = ?1 and establishment.id = ?2", idEnterprise, idEstabelecimento).firstResult();
    }

}
