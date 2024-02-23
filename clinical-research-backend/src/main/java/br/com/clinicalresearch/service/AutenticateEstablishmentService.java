package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.*;
import br.com.clinicalresearch.dto.AutenticateEstablishmentRequest;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NotFoundException;
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

    public AutenticateEstablishment getAutenticateEstablishmentById(Long idAutenticateEstablishment) throws BusinessException {
        AutenticateEstablishment existingEnterpriseEstablishment = autenticateEstablishmentRepository.findById(idAutenticateEstablishment);

        if (existingEnterpriseEstablishment == null) {
            throw new BusinessException("Autenticate Establishment not registered with the ID " + idAutenticateEstablishment);
        }
        return existingEnterpriseEstablishment;
    }

    public AutenticateEstablishment updateAutenticateEstablishment(Long idAutenticateEstablishment, AutenticateEstablishment autenticateEstablishment) throws NotFoundException {
        AutenticateEstablishment existingAutenticateEstablishment = autenticateEstablishmentRepository.findById(idAutenticateEstablishment);

        if (existingAutenticateEstablishment == null) {
            throw new NotFoundException("Autenticate Establishment not registered with the ID " + idAutenticateEstablishment);
        } else {
            existingAutenticateEstablishment.setStatus(autenticateEstablishment.getStatus());
            autenticateEstablishmentRepository.persist(existingAutenticateEstablishment);
        }
        return existingAutenticateEstablishment;
    }


    public AutenticateEstablishment addAutenticateEstablishment(AutenticateEstablishmentRequest autenticateEstablishmentRequest) throws NotFoundException, BusinessException {

        Long idAutenticate = autenticateEstablishmentRequest.idAutenticate();
        Long idEstablishment = autenticateEstablishmentRequest.idEstablishment();

        Autenticate autenticate = autenticateService.getAutenticateById(idAutenticate);
        if (autenticate == null) {
            throw new NotFoundException("Autenticate not found with ID: " + idAutenticate);
        }

        Establishment establishment = establishmentService.getEstablishmentById(idEstablishment);
        if (establishment == null) {
            throw new NotFoundException("Establishment not found with ID: " + idEstablishment);
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
    public void removeAutenticateEstablishment(Long idAutenticateEstablishment) throws NotFoundException {

        AutenticateEstablishment existingAutenticateEstablishment = autenticateEstablishmentRepository.findById(idAutenticateEstablishment);

        if (existingAutenticateEstablishment == null) {
            throw new NotFoundException("Autenticate Establishment not found with ID: " + idAutenticateEstablishment);
        } else {
            autenticateEstablishmentRepository.delete(existingAutenticateEstablishment);
        }
    }


}
