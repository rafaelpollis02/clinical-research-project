package br.com.clinicalresearch.service;

import br.com.clinicalresearch.collection.StatusObject;
import br.com.clinicalresearch.domain.Autenticate;
import br.com.clinicalresearch.domain.AutenticateToken;
import br.com.clinicalresearch.domain.Person;
import br.com.clinicalresearch.dto.AutenticateRequest;
import br.com.clinicalresearch.dto.AutenticateResponse;
import br.com.clinicalresearch.exceptions.BadRequestException;
import br.com.clinicalresearch.exceptions.InvalidLoginException;
import br.com.clinicalresearch.exceptions.NotFoundException;
import br.com.clinicalresearch.repository.AutenticateRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
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

    public String getAccessByCpfOrEmail(AutenticateRequest autenticateRequest) throws InvalidLoginException, NotFoundException {

        String user = autenticateRequest.user();
        String password = autenticateRequest.password();

        if (validateAutenticateByCpf(user)) {
            String existingPassword = autenticateRepository.findPasswordByCpf(user);
            String passwordDecoder = decodePassword(existingPassword);
            if (password.equals(passwordDecoder)) {
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

    public Autenticate createAutenticate(Autenticate autenticate, @Valid Person person) {
        autenticate.setPerson(person);
        String passwordEncode = generatePasswordEncoder();
        autenticate.setPassword(passwordEncode);
        autenticate.setPasswordDecodificado(decodePassword(passwordEncode));
        autenticateRepository.persist(autenticate);
        return autenticate;
    }

    public Autenticate updatePasswordAutenticate(String user, AutenticateRequest autenticateRequest) throws BadRequestException, NotFoundException {

        String password = autenticateRequest.password();

        if (validateAutenticateByCpf(user)) {
            Autenticate existingAutenticate = autenticateRepository.findAutenticateByCpf(user);
            existingAutenticate.setPassword(encodePassword(password));
            existingAutenticate.setPasswordDecodificado(password);
            existingAutenticate.setUpdateDate(LocalDateTime.now());
            existingAutenticate.setStatus(StatusObject.valueOf("ACTIVE"));
            Response.status(Response.Status.OK).build();

        } else if (validateAutenticateByEmail(user)) {
            Autenticate existingAutenticate = autenticateRepository.findAutenticateByEmail(user);
            existingAutenticate.setPassword(password);
            existingAutenticate.setUpdateDate(LocalDateTime.now());
            existingAutenticate.setStatus(StatusObject.valueOf("ACTIVE"));
            Response.status(Response.Status.OK).build();
        }
        return null;
    }

    public String generateToken(AutenticateRequest autenticateRequest) throws NotFoundException {

        String user = autenticateRequest.user();

        if (validateAutenticateByCpf(user)) {
            Autenticate autenticate = autenticateRepository.findAutenticateByCpf(user);
            return tokenForAutenticate(autenticate);
        }

        if (validateAutenticateByEmail(user)) {
            Autenticate autenticate = autenticateRepository.findAutenticateByEmail(user);
            return tokenForAutenticate(autenticate);
        }
        throw new NotFoundException("User not found");
    }

    public AutenticateResponse validateToken(String token) throws BadRequestException, NotFoundException {

        AutenticateToken existingAutenticateToken = autenticateTokenService.findTokenByToken(token);

        if (existingAutenticateToken != null) {
            String tokenRecuperado = String.valueOf(existingAutenticateToken.getToken());
            LocalDateTime expireDate = existingAutenticateToken.getExpireDate();

            if (token.equals(tokenRecuperado) && expireDate.isAfter(LocalDateTime.now())) {

                AutenticateResponse autenticateResponse = new AutenticateResponse();
                autenticateResponse.setCpf(existingAutenticateToken.getAutenticate().getCpf());
                autenticateResponse.setEmail(existingAutenticateToken.getAutenticate().getEmail());
                autenticateResponse.setFullName(existingAutenticateToken.getAutenticate().getPerson().getFullName());

                return autenticateResponse;
            } else {
                throw new BadRequestException("Token is Invalid");
            }
        } else {
            throw new NotFoundException("Token not found");
        }
    }

    public Autenticate updatePasswordAutenticate(Long idAutenticate, Autenticate autenticate) {
        Autenticate existingAutenticate = autenticateRepository.findById(idAutenticate);
        existingAutenticate.setPassword(autenticate.getPassword());
        existingAutenticate.setUpdateDate(LocalDateTime.now());
        existingAutenticate.setStatus(StatusObject.valueOf("ACTIVE"));
        autenticateRepository.persist(existingAutenticate);
        return existingAutenticate;
    }

    public boolean validateAutenticateByCpf(String cpf) throws NotFoundException {
        Autenticate existingAutenticate = autenticateRepository.findAutenticateByCpf(cpf);
        if (existingAutenticate == null) {
            throw new NotFoundException("User not found");
        } else {
            return true;
        }
    }

    public boolean validateAutenticateByEmail(String email) throws NotFoundException {
        Autenticate existingAutenticate = autenticateRepository.findAutenticateByEmail(email);
        if (existingAutenticate == null) {
            throw new NotFoundException("User not found");
        } else {
            return true;
        }
    }

    public String generatePasswordEncoder() {
        int password = 100000 + random.nextInt(900000);
        return Base64.getEncoder().encodeToString(String.valueOf(password).getBytes());
    }

    public String decodePassword(String passwordEncode) {
        byte[] decodedBytes = Base64.getDecoder().decode(passwordEncode);
        return new String(decodedBytes);
    }

    public String encodePassword(String passwordDecode) {
        byte[] decodedBytes = Base64.getEncoder().encode(passwordDecode.getBytes());
        return new String(decodedBytes);
    }

    public String tokenForAutenticate(Autenticate autenticate) {
        AutenticateToken autenticateToken = new AutenticateToken();
        autenticateToken.setToken(autenticateTokenService.gerarToken());
        autenticateToken.setAutenticate(autenticate);
        autenticateTokenService.createAutenticateToken(autenticateToken);
        return Response.ok("Successful").build().toString();
    }
}
