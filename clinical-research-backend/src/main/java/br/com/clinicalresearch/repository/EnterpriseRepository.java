package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.Enterprise;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class EnterpriseRepository implements PanacheRepository<Enterprise> {

    public Enterprise findEnterpriseByCnpj(String cnpj){
        return find("cnpj", cnpj).firstResult();
    }
    public Enterprise findEnterpriseByName(String name) {
        return find("name", name).firstResult();
    }

}
