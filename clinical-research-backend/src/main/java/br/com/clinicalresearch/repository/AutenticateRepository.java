package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.Autenticate;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class AutenticateRepository implements PanacheRepository<Autenticate> {

    public Autenticate findAutenticateByEmail(String email) {
        return find("email", email).firstResult();
    }

    public Autenticate findAutenticateByCpf(String cpf) {
        return find("cpf", cpf).firstResult();
    }

    public String findPasswordByCpf(String cpf) {
        Autenticate autenticate = find("cpf", cpf).singleResult();
        return autenticate.getPassword();
    }

    public String findPasswordByEmail(String email) {
        Autenticate autenticate = find("email", email).singleResult();
        return autenticate.getPassword();
    }

}
