package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Enterprise;
import br.com.clinicalresearch.domain.EnterpriseEstablishment;
import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NotFoundException;
import br.com.clinicalresearch.repository.EnterpriseEstablishmentRepository;
import br.com.clinicalresearch.repository.EnterpriseRepository;
import br.com.clinicalresearch.repository.EstablishmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EnterpriseService {

    @Inject
    EnterpriseRepository enterpriseRepository;

    @Inject
    EstablishmentRepository establishmentRepository;

    @Inject
    EnterpriseEstablishmentRepository enterpriseEstablishmentRepository;

    public List<Enterprise> getAllEnterprise() {
        return enterpriseRepository.listAll();
    }

    public Enterprise getEnterpriseById(Long idEnterprise) throws NotFoundException {
        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);

        if (existingEnterprise == null) {
            throw new NotFoundException("Enterprise not registered with the ID " + idEnterprise);
        }
        return existingEnterprise;
    }

    public Enterprise createEnterprise(Enterprise enterprise) throws BusinessException {
        Optional<Enterprise> existingEnterprise = enterpriseRepository.findEnterpriseByCnpj(enterprise.getCnpj());
        if (existingEnterprise.isPresent()) {
            throw new BusinessException("Enterprise duplicate by cnpj " + enterprise.getCnpj());
        } else {
            enterpriseRepository.persist(enterprise);
        }
        return enterprise;
    }

    public Enterprise updateEnterprise(Long idEnterprise, Enterprise enterprise) throws NotFoundException {
        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);

        if (existingEnterprise == null) {
            throw new NotFoundException("Enterprise not registered with the ID " + idEnterprise);
        } else {
            existingEnterprise.setCnpj(enterprise.getCnpj());
            existingEnterprise.setName(enterprise.getName());
            existingEnterprise.setStatus(enterprise.getStatus());
            existingEnterprise.setUpdateDate(LocalDateTime.now());
            enterpriseRepository.persist(existingEnterprise);
        }
        return existingEnterprise;
    }

    public void deleteEnterprise(Long idEnterprise) throws BusinessException {
        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);

        if (existingEnterprise == null) {
            throw new BusinessException("Enterprise not registered with the ID " + idEnterprise);
        } else {
            enterpriseRepository.delete(existingEnterprise);
        }
    }

    public Enterprise addEstablishment(Long idEnterprise, Establishment establishment) throws BusinessException {

        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);

        if (existingEnterprise == null) {
            throw new BusinessException("Enterprise not registered with the ID " + idEnterprise);
        }

        Long idEstablishment = establishment.getId();
        Establishment existingEstablishment = establishmentRepository.findById(idEstablishment);

        if (existingEstablishment == null) {
            throw new BusinessException("Establishment not registered with the ID " + idEstablishment);
        }

        EnterpriseEstablishment enterpriseEstablishment = new EnterpriseEstablishment();
        enterpriseEstablishment.setEnterprise(existingEnterprise);
        enterpriseEstablishment.setEstablishment(existingEstablishment);

        existingEnterprise.getEnterpriseEstablishments().add(enterpriseEstablishment);

        enterpriseRepository.persist(existingEnterprise);

        return existingEnterprise;

    }

    public Enterprise removeEstablishment(Long idEnterprise, Establishment establishment) throws BusinessException {
        return null;
    }

}