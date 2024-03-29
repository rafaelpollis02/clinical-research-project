package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.AutenticateToken;
import br.com.clinicalresearch.repository.AutenticateTokenRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Random;

@ApplicationScoped
public class AutenticateTokenService {

    @Inject
    AutenticateTokenRepository autenticateTokenRepository;

    private final Random random = new Random();

    public AutenticateToken saveToken(AutenticateToken autenticateToken) {
        autenticateTokenRepository.persist(autenticateToken);
        return autenticateToken;
    }

    public int gerarToken() {
        return 100000 + random.nextInt(900000);
    }

}
