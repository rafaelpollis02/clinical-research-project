package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.Enterprise;
import br.com.clinicalresearch.domain.Establishment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EnterpriseRepository implements PanacheRepository<Enterprise> {

    public List<Enterprise> findByName(String name) {
        return list("lower(name) LIKE ?1", "%" + name.toLowerCase() + "%");
    }
    public Enterprise findByCnpj(String cnpj){
        return find("cnpj", cnpj).firstResult();
    }

}
