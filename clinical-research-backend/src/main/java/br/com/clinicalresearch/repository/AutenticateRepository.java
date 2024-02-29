package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.Autenticate;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AutenticateRepository implements PanacheRepository<Autenticate> {

    public Autenticate findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public Autenticate findByCpf(String cpf) {
        return find("cpf", cpf).firstResult();
    }

    public String findPasswordByCpf(String cpf) {
        Autenticate autenticate = find("cpf", cpf).firstResult();
        return autenticate.getPassword();
    }

    public String findPasswordByEmail(String email) {
        Autenticate autenticate = find("email", email).firstResult();
        return autenticate.getPassword();
    }

}
