package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.AutenticateToken;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AutenticateTokenRepository implements PanacheRepository<AutenticateToken> {

    @PersistenceContext
    EntityManager entityManager;

    public AutenticateToken findTokenByAutenticateId(Long idAutenticate) {
        return find("autenticate.id = ?1 order by createDate desc", idAutenticate).firstResult();
    }

}
