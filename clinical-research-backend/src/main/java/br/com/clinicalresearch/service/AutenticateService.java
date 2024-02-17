package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Autenticate;
import br.com.clinicalresearch.domain.AutenticateToken;
import br.com.clinicalresearch.domain.Person;
import br.com.clinicalresearch.dto.AutenticateRequest;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.repository.AutenticateRepository;
import br.com.clinicalresearch.repository.AutenticateTokenRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.Base64;
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

    public String getAutenticateByCpfOrEmail(AutenticateRequest autenticateRequest) throws BusinessException {

        String user = autenticateRequest.user();
        String password = autenticateRequest.password();

        Autenticate existingAutenticateCpf = autenticateRepository.findAutenticateByCpf(user);
        Autenticate existingAutenticateEmail = null;
        String existingPassword = null;

        if (existingAutenticateCpf != null) {
            existingPassword = autenticateRepository.findPasswordByCpf(user);
        } else {
            existingAutenticateEmail = autenticateRepository.findAutenticateByEmail(user);
            if (existingAutenticateEmail != null) {
                existingPassword = autenticateRepository.findPasswordByEmail(user);
            }
        }

        if (existingPassword != null && existingPassword.equals(password)) {
            return "Sucessful";
        } else {
            throw new BusinessException("Login or Password Invalid");
        }
    }

    public Autenticate saveAutenticate(Autenticate autenticate, Person person) {

        autenticate.setPerson(person);
        autenticate.setPassword(gerarPasswordEncoder());
        autenticateRepository.persist(autenticate);

        AutenticateToken autenticateToken = new AutenticateToken();
        autenticateToken.setToken(autenticateTokenService.gerarToken());
        autenticateToken.setAutenticate(autenticate);

        autenticateTokenRepository.persist(autenticateToken);

        return autenticate;
    }

    public Autenticate updatePasswordAutenticate(Long idAutenticate, Autenticate autenticate) {
        Autenticate existingAutenticate = autenticateRepository.findById(idAutenticate);
        existingAutenticate.setPassword(autenticate.getPassword());
        existingAutenticate.setUpdateDate(LocalDateTime.now());
        existingAutenticate.setFirstAcess(false);
        autenticateRepository.persist(existingAutenticate);
        return existingAutenticate;
    }

    public String gerarPasswordEncoder() {
        int password = 100000 + random.nextInt(900000);
        return Base64.getEncoder().encodeToString(String.valueOf(password).getBytes());
    }

    public String decodificarPassword(String passwordCodificada) {
        byte[] decodedBytes = Base64.getDecoder().decode(passwordCodificada);
        return new String(decodedBytes);
    }
}
