package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Enterprise;
import br.com.clinicalresearch.domain.EnterpriseEstablishment;
import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.dto.EnterpriseEstablishmentRequest;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NotFoundException;
import br.com.clinicalresearch.repository.EnterpriseEstablishmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class EnterpriseEstablishmentService {

    @Inject
    EnterpriseEstablishmentRepository enterpriseEstablishmentRepository;

    @Inject
    EnterpriseService enterpriseService;

    @Inject
    EstablishmentService establishmentService;

    public List<EnterpriseEstablishment> getAllEnterpriseEstablishment() {
        return enterpriseEstablishmentRepository.listAll();
    }

    public EnterpriseEstablishment getEnterpriseEstablishmentById(Long idEnterpriseEstablishment) throws BusinessException {
        EnterpriseEstablishment existingEnterpriseEstablishment = enterpriseEstablishmentRepository.findById(idEnterpriseEstablishment);

        if (existingEnterpriseEstablishment == null) {
            throw new BusinessException("Enterprise Establishment not registered with the ID " + idEnterpriseEstablishment);
        }
        return existingEnterpriseEstablishment;
    }

    public EnterpriseEstablishment addEnterpriseEstablishment(EnterpriseEstablishmentRequest enterpriseEstablishmentRequest) throws NotFoundException, BusinessException {

        Long idEnterprise = enterpriseEstablishmentRequest.getIdEnterprise();
        Long idEstablishment = enterpriseEstablishmentRequest.getIdEstablishment();

        Enterprise enterprise = enterpriseService.getEnterpriseById(idEnterprise);
        if (enterprise == null) {
            throw new NotFoundException("Enterprise not found with ID: " + idEnterprise);
        }

        Establishment establishment = establishmentService.getEstablishmentById(idEstablishment);
        if (establishment == null) {
            throw new NotFoundException("Establishment not found with ID: " + idEstablishment);
        }

        EnterpriseEstablishment existingRelationship = enterpriseEstablishmentRepository.findByIdEnterpriseAndIdEstabelecimento(idEnterprise, idEstablishment);
        if (existingRelationship != null) {
            throw new BusinessException("There is already a relationship between this enterprise and establishment");
        } else {
            EnterpriseEstablishment enterpriseEstablishment = new EnterpriseEstablishment();
            enterpriseEstablishment.setEnterprise(enterprise);
            enterpriseEstablishment.setEstablishment(establishment);
            enterpriseEstablishmentRepository.persist(enterpriseEstablishment);
            return enterpriseEstablishment;
        }
    }

    public void removeEnterpriseEstablishment(Long idEnterpriseEstablishment) throws NotFoundException {

        EnterpriseEstablishment existingEnterpriseEstablishment = enterpriseEstablishmentRepository.findById(idEnterpriseEstablishment);

        if (existingEnterpriseEstablishment == null) {
            throw new NotFoundException("Enterprise Establishment not found with ID: " + idEnterpriseEstablishment);
        } else {
            enterpriseEstablishmentRepository.delete(existingEnterpriseEstablishment);
        }
    }
}
