package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.AutenticateToken;
import br.com.clinicalresearch.repository.AutenticateTokenRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.Random;

@ApplicationScoped
public class AutenticateTokenService {

    @Inject
    AutenticateTokenRepository autenticateTokenRepository;

    private final Random random = new Random();

    public AutenticateToken findTokenByAutenticateId(Long idAutenticate) {
        return autenticateTokenRepository.findTokenByAutenticateId(idAutenticate);
    }

    public AutenticateToken findTokenByToken(String token) {
        return autenticateTokenRepository.findTokenByToken(token);
    }

    public AutenticateToken createAutenticateToken(AutenticateToken autenticateToken) {
        autenticateToken.setExpireDate(LocalDateTime.now().plusMinutes(30));
        autenticateTokenRepository.persist(autenticateToken);
        return autenticateToken;
    }

    public AutenticateToken saveToken(AutenticateToken autenticateToken) {
        autenticateTokenRepository.persist(autenticateToken);
        return autenticateToken;
    }

    public int gerarToken() {
        return 100000 + random.nextInt(900000);
    }

}
