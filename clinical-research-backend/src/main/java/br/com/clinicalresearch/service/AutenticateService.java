package br.com.clinicalresearch.service;

import br.com.clinicalresearch.collection.StatusObject;
import br.com.clinicalresearch.domain.Autenticate;
import br.com.clinicalresearch.domain.AutenticateToken;
import br.com.clinicalresearch.domain.Person;
import br.com.clinicalresearch.dto.AutenticateRequest;
import br.com.clinicalresearch.dto.AutenticateResponse;
import br.com.clinicalresearch.exceptions.*;
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

    public String getAutenticateByCpfOrEmail(String user) throws NoContentException {

        if (validateAutenticateByCpf(user) || validateAutenticateByEmail(user)) {
            return Response.ok("Successful").build().toString();
        } else {
            throw new NoContentException();
        }
    }

    public String getAccessByCpfOrEmail(AutenticateRequest autenticateRequest) throws InvalidLoginException, NoContentException {

        String user = autenticateRequest.user();
        String password = autenticateRequest.password();

        if (validateAutenticateByCpf(user)) {
            String existingPassword = autenticateRepository.findPasswordByCpf(user);
            String passwordDecoder = decodePassword(existingPassword);
            if (password.equals(passwordDecoder)) {
                return Response.ok("Successful").build().toString();
            } else {
                throw new InvalidLoginException();
            }
        } else if (validateAutenticateByEmail(user)) {
            String existingPassword = autenticateRepository.findPasswordByEmail(user);
            String passwordDecoder = decodePassword(existingPassword);
            if (password.equals(passwordDecoder)) {
                return Response.ok("Successful").build().toString();
            } else {
                throw new InvalidLoginException();
            }
        }

        if (validateAutenticateByCpf(user) == false && validateAutenticateByEmail(user) == false) {
            throw new InvalidLoginException();
        }
        return user;
    }

    public Autenticate getAutenticateById(Long idAutenticate) {
        Autenticate existingAutenticate = autenticateRepository.findById(idAutenticate);
        return existingAutenticate;
    }

    public Autenticate createAutenticate(Autenticate autenticate, @Valid Person person) {
        autenticate.setPerson(person);
        String passwordEncode = generatePasswordEncoder();
        autenticate.setPassword(passwordEncode);
        autenticate.setPasswordDecodificado(decodePassword(passwordEncode));
        autenticateRepository.persist(autenticate);
        return autenticate;
    }

    public Autenticate updateAutenticate(Long idAutenticate, Autenticate autenticate) throws NoContentException {
        Autenticate existingAutenticate = autenticateRepository.findById(idAutenticate);

        if (existingAutenticate == null) {
            throw new NoContentException();
        } else {
            existingAutenticate.setStatus(autenticate.getStatus());
            autenticateRepository.persist(existingAutenticate);
        }
        return existingAutenticate;
    }

    public Autenticate updatePasswordAutenticate(String user, AutenticateRequest autenticateRequest) throws NoContentException {

        String password = autenticateRequest.password();

        if (validateAutenticateByCpf(user)) {
            Autenticate existingAutenticate = autenticateRepository.findByCpf(user);
            existingAutenticate.setPassword(encodePassword(password));
            existingAutenticate.setPasswordDecodificado(password);
            existingAutenticate.setUpdateDate(LocalDateTime.now());
            existingAutenticate.setStatus(StatusObject.valueOf("ACTIVE"));
            Response.status(Response.Status.OK).build();

        } else if (validateAutenticateByEmail(user)) {
            Autenticate existingAutenticate = autenticateRepository.findByEmail(user);
            existingAutenticate.setPassword(password);
            existingAutenticate.setUpdateDate(LocalDateTime.now());
            existingAutenticate.setStatus(StatusObject.valueOf("ACTIVE"));
            Response.status(Response.Status.OK).build();
        }
        return null;
    }

    public String generateToken(AutenticateRequest autenticateRequest) throws NoContentException {
        String user = autenticateRequest.user();

        if (validateAutenticateByCpf(user)) {
            Autenticate autenticate = autenticateRepository.findByCpf(user);
            return tokenForAutenticate(autenticate);
        }

        if (validateAutenticateByEmail(user)) {
            Autenticate autenticate = autenticateRepository.findByEmail(user);
            return tokenForAutenticate(autenticate);
        }
        throw new NoContentException();
    }

    public AutenticateResponse validateToken(String token) throws BusinessException, NoContentException {

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
                throw new BusinessException("Token is Invalid");
            }
        } else {
            throw new NoContentException();
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

    public boolean validateAutenticateByCpf(String cpf) throws NoContentException {
        Autenticate existingAutenticate = autenticateRepository.findByCpf(cpf);
        if (existingAutenticate == null) {
            throw new NoContentException();
        } else {
            return true;
        }
    }

    public boolean validateAutenticateByEmail(String email) throws NoContentException {
        Autenticate existingAutenticate = autenticateRepository.findByEmail(email);
        if (existingAutenticate == null) {
            throw new NoContentException();
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
