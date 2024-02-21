package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Autenticate;
import br.com.clinicalresearch.domain.AutenticateToken;
import br.com.clinicalresearch.domain.Person;
import br.com.clinicalresearch.dto.AutenticateRequest;
import br.com.clinicalresearch.exceptions.BadRequestException;
import br.com.clinicalresearch.exceptions.InvalidLoginException;
import br.com.clinicalresearch.exceptions.NotFoundException;
import br.com.clinicalresearch.repository.AutenticateRepository;
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


    private final Random random = new Random();

    public String getAutenticateByCpfOrEmail(String user) throws NotFoundException {

        System.out.println(user);

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

    public Autenticate createAutenticate(Autenticate autenticate, Person person) {
        autenticate.setPerson(person);
        autenticate.setPassword(gerarPasswordEncoder());
        autenticateRepository.persist(autenticate);
        return autenticate;
    }

    public Autenticate updatePasswordAutenticate(AutenticateRequest autenticateRequest) throws BadRequestException {

        String user = autenticateRequest.user();
        String password = autenticateRequest.password();
        String token = autenticateRequest.token();

        Autenticate existingAutenticate = new Autenticate();
        AutenticateToken existingAutenticateToken = new AutenticateToken();

        if (validateAutenticateByCpf(user)) {
            existingAutenticate = autenticateRepository.findAutenticateByCpf(user);
            existingAutenticateToken = autenticateTokenService.findTokenByAutenticateId(existingAutenticate.getId());
            LocalDateTime expireDate = existingAutenticateToken.getExpireDate();
            if (Integer.toString(existingAutenticateToken.getToken()).equals(token) && expireDate.isAfter(LocalDateTime.now())) {
                existingAutenticate.setPassword(password);
                existingAutenticate.setUpdateDate(LocalDateTime.now());
                existingAutenticate.setFirstAcess(false);
                Response.status(Response.Status.OK).build();
            } else {
                throw new BadRequestException("Token is Invalid");
            }

        } else if (validateAutenticateByEmail(user)) {
            existingAutenticate = autenticateRepository.findAutenticateByEmail(user);
            existingAutenticateToken = autenticateTokenService.findTokenByAutenticateId(existingAutenticate.getId());
            LocalDateTime expireDate = existingAutenticateToken.getExpireDate();
            if (Integer.toString(existingAutenticateToken.getToken()).equals(token) && expireDate.isAfter(LocalDateTime.now())) {
                existingAutenticate.setPassword(password);
                existingAutenticate.setUpdateDate(LocalDateTime.now());
                existingAutenticate.setFirstAcess(false);
                Response.status(Response.Status.OK).build();
            } else {
                throw new BadRequestException("Token is Invalid");
            }
        }
        return null;
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

    public void validateToken(String token) throws BadRequestException, NotFoundException {

        AutenticateToken existingAutenticateToken = autenticateTokenService.findTokenByToken(token);

        if (existingAutenticateToken != null) {
            String tokenRecuperado = String.valueOf(existingAutenticateToken.getToken());
            LocalDateTime expireDate = existingAutenticateToken.getExpireDate();

            if (token.equals(tokenRecuperado) && expireDate.isAfter(LocalDateTime.now())) {
                Response.status(Response.Status.OK).build();
                System.out.println("passou na validação de igual de tokens e não expirado");

            } else {
                System.out.println("passou no else de token diferente ou data expirada");
                throw new BadRequestException("Token is Invalid");
            }
        } else {
            throw new NotFoundException("Token not found");
        }

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

    public String generateTokenForAutenticate(Autenticate autenticate) {
        AutenticateToken autenticateToken = new AutenticateToken();
        autenticateToken.setToken(autenticateTokenService.gerarToken());
        autenticateToken.setAutenticate(autenticate);
        autenticateTokenService.createAutenticateToken(autenticateToken);
        return Response.ok("Successful").build().toString();
    }
}
