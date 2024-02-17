package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.Enterprise;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class EnterpriseRepository implements PanacheRepository<Enterprise> {

    public Optional<Enterprise> findEnterpriseByCnpj(String cnpj){
        return find("cnpj", cnpj).singleResultOptional();
    }

    public Optional<Enterprise> findEnterpriseByName(String name) {
        return find("name", name).singleResultOptional();
    }

}
