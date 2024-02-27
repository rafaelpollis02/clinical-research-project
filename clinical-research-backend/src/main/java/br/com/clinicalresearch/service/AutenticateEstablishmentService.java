package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Autenticate;
import br.com.clinicalresearch.domain.AutenticateEstablishment;
import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.dto.AutenticateEstablishmentRequest;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NoContentException;
import br.com.clinicalresearch.repository.AutenticateEstablishmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class AutenticateEstablishmentService {

    @Inject
    AutenticateEstablishmentRepository autenticateEstablishmentRepository;

    @Inject
    AutenticateService autenticateService;

    @Inject
    EstablishmentService establishmentService;

    public List<AutenticateEstablishment> getAllAutenticateEstablishment() {
        return autenticateEstablishmentRepository.listAll();
    }

    public AutenticateEstablishment getAutenticateEstablishmentById(Long idAutenticateEstablishment) throws NoContentException {
        AutenticateEstablishment existingEnterpriseEstablishment = autenticateEstablishmentRepository.findById(idAutenticateEstablishment);

        if (existingEnterpriseEstablishment == null) {
            throw new NoContentException();
        }
        return existingEnterpriseEstablishment;
    }

    public AutenticateEstablishment updateAutenticateEstablishment(Long idAutenticateEstablishment, AutenticateEstablishment autenticateEstablishment) throws NoContentException {
        AutenticateEstablishment existingAutenticateEstablishment = autenticateEstablishmentRepository.findById(idAutenticateEstablishment);

        if (existingAutenticateEstablishment == null) {
            throw new NoContentException();
        } else {
            existingAutenticateEstablishment.setStatus(autenticateEstablishment.getStatus());
            autenticateEstablishmentRepository.persist(existingAutenticateEstablishment);
        }
        return existingAutenticateEstablishment;
    }


    public AutenticateEstablishment addAutenticateEstablishment(AutenticateEstablishmentRequest autenticateEstablishmentRequest) throws NoContentException, BusinessException {

        Long idAutenticate = autenticateEstablishmentRequest.idAutenticate();
        Long idEstablishment = autenticateEstablishmentRequest.idEstablishment();

        Autenticate autenticate = autenticateService.getAutenticateById(idAutenticate);
        if (autenticate == null) {
            throw new NoContentException();
        }

        Establishment establishment = establishmentService.getEstablishmentById(idEstablishment);
        if (establishment == null) {
            throw new NoContentException();
        }

        AutenticateEstablishment existingRelationship = autenticateEstablishmentRepository.findByIdAutenticateAndIdEstablishmento(idAutenticate, idEstablishment);
        if (existingRelationship != null) {
            throw new BusinessException("There is already a relationship between this autenticate and establishment");
        } else {
            AutenticateEstablishment autenticateEstablishment = new AutenticateEstablishment();
            autenticateEstablishment.setAutenticate(autenticate);
            autenticateEstablishment.setEstablishment(establishment);
            autenticateEstablishmentRepository.persist(autenticateEstablishment);
            return autenticateEstablishment;
        }
    }

    @Transactional
    public void removeAutenticateEstablishment(Long idAutenticateEstablishment) throws NoContentException {

        AutenticateEstablishment existingAutenticateEstablishment = autenticateEstablishmentRepository.findById(idAutenticateEstablishment);

        if (existingAutenticateEstablishment == null) {
            throw new NoContentException();
        } else {
            autenticateEstablishmentRepository.delete(existingAutenticateEstablishment);
        }
    }
}
