package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Autenticate;
import br.com.clinicalresearch.domain.AutenticateToken;
import br.com.clinicalresearch.domain.Person;
import br.com.clinicalresearch.repository.AutenticateRepository;
import br.com.clinicalresearch.repository.AutenticateTokenRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Base64;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class AutenticateService {

    @Inject
    AutenticateRepository autenticateRepository;

    @Inject
    AutenticateTokenService autenticateTokenService;

    @Inject
    AutenticateTokenRepository autenticateTokenRepository;

    private final Random random = new Random();

    public List<Autenticate> getAllAutenticate() {
        return autenticateRepository.listAll();
    }

    public Autenticate saveAutenticate(Autenticate autenticate) {

        autenticateRepository.persist(autenticate);

        AutenticateToken autenticateToken = new AutenticateToken();
        autenticateToken.setToken(autenticateTokenService.gerarToken());
        autenticateToken.setAutenticate(autenticate);

        autenticateTokenRepository.persist(autenticateToken);

        return autenticate;
    }

    public String gerarPassword() {
        int password = 100000 + random.nextInt(900000);
        return Base64.getEncoder().encodeToString(String.valueOf(password).getBytes());
    }

}
