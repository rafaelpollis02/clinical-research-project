package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.AutenticateToken;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AutenticateTokenRepository implements PanacheRepository<AutenticateToken> {
}
