package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Autenticate;
import br.com.clinicalresearch.domain.AutenticateToken;
import br.com.clinicalresearch.domain.Person;
import br.com.clinicalresearch.dto.AutenticateRequest;
import br.com.clinicalresearch.exceptions.InvalidLoginException;
import br.com.clinicalresearch.exceptions.NotFoundException;
import br.com.clinicalresearch.repository.AutenticateRepository;
import br.com.clinicalresearch.repository.AutenticateTokenRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;

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

    public String getAutenticateByCpfOrEmail(AutenticateRequest autenticateRequest) throws NotFoundException {

        String user = autenticateRequest.user();

        if (validateAutenticateByCpf(user) || validateAutenticateByEmail(user)) {
            return Response.ok("Successful").build().toString();
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public String getAccessByCpfOrEmail(AutenticateRequest autenticateRequest) throws InvalidLoginException {

        String user = autenticateRequest.user();
        String password = autenticateRequest.password();

        if (validateAutenticateByCpf(user)) {
            String existingPassword = autenticateRepository.findPasswordByCpf(user);
            if (password.equals(existingPassword)) {
                return Response.ok("Successful").build().toString();
            } else {
                throw new InvalidLoginException("User or Password Invalid");
            }

        } else if (validateAutenticateByEmail(user)) {
            String existingPassword = autenticateRepository.findPasswordByEmail(user);
            if (password.equals(existingPassword)) {
                return Response.ok("Successful").build().toString();
            } else {
                throw new InvalidLoginException("User or Password Invalid");
            }
        }

        if (validateAutenticateByCpf(user) == false && validateAutenticateByEmail(user) == false) {
            throw new InvalidLoginException("User or Password Invalid");
        }
        return user;
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

    public String generateToken(AutenticateRequest autenticateRequest) throws NotFoundException {

        String user = autenticateRequest.user();

        if (validateAutenticateByCpf(user)) {
            Autenticate autenticate = autenticateRepository.findAutenticateByCpf(user);
            return generateTokenForAutenticate(autenticate);
        }

        if (validateAutenticateByEmail(user)) {
            Autenticate autenticate = autenticateRepository.findAutenticateByEmail(user);
            return generateTokenForAutenticate(autenticate);
        }
        throw new NotFoundException("User not found");
    }

    public Autenticate updatePasswordAutenticate(AutenticateRequest autenticateRequest) {

        //Passar recuperar o Token para passar junto no Body (user, password, e token), se token expirado cancela.

        String user = autenticateRequest.user();
        String password = autenticateRequest.password();

        System.out.println(validateAutenticateByCpf(user));
        System.out.println(validateAutenticateByEmail(user));

        return null;
    }


    public boolean validateAutenticateByCpf(String cpf) {

        Autenticate existingAutenticate = autenticateRepository.findAutenticateByCpf(cpf);
        if (existingAutenticate == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validateAutenticateByEmail(String email) {

        Autenticate existingAutenticate = autenticateRepository.findAutenticateByEmail(email);
        if (existingAutenticate == null) {
            return false;
        } else {
            return true;
        }
    }

    public String gerarPasswordEncoder() {
        int password = 100000 + random.nextInt(900000);
        return Base64.getEncoder().encodeToString(String.valueOf(password).getBytes());
    }

    public String decodificarPassword(String passwordCodificada) {
        byte[] decodedBytes = Base64.getDecoder().decode(passwordCodificada);
        return new String(decodedBytes);
    }

    public String generateTokenForAutenticate(Autenticate autenticate) {
        AutenticateToken autenticateToken = new AutenticateToken();
        autenticateToken.setToken(autenticateTokenService.gerarToken());
        autenticateToken.setAutenticate(autenticate);
        autenticateTokenRepository.persist(autenticateToken);
        return Response.ok("Successful").build().toString();
    }
}
