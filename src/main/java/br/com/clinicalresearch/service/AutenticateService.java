package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Autenticate;
import br.com.clinicalresearch.domain.AutenticateToken;
import br.com.clinicalresearch.repository.AutenticateRepository;
import br.com.clinicalresearch.repository.AutenticateTokenRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class AutenticateService {

    @Inject
    AutenticateRepository autenticateRepository;

    @Inject
    AutenticateTokenService autenticateTokenService;

    @Inject
    AutenticateTokenRepository autenticateTokenRepository;

    public List<Autenticate> getAllAutenticate() {
        return autenticateRepository.listAll();
    }

    public Autenticate saveAutenticate(Autenticate autenticate) {
        autenticateRepository.persist(autenticate);

        AutenticateToken autenticateToken = new AutenticateToken();
        autenticateToken.setToken(autenticateTokenService.gerarToken());
        autenticateToken.setIdAutenticate(autenticate.getId());
        autenticateTokenRepository.persist(autenticateToken);

        return autenticate;
    }


}
