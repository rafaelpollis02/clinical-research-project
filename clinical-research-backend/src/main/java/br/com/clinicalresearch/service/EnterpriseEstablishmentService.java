package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Enterprise;
import br.com.clinicalresearch.domain.EnterpriseEstablishment;
import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.dto.EnterpriseEstablishmentRequest;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NoContentException;
import br.com.clinicalresearch.repository.EnterpriseEstablishmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class EnterpriseEstablishmentService {

    @Inject
    EnterpriseEstablishmentRepository enterpriseEstablishmentRepository;

    @Inject
    EnterpriseService enterpriseService;

    @Inject
    EstablishmentService establishmentService;

    public List<EnterpriseEstablishment> getAllEnterpriseEstablishment() throws NoContentException {
        List<EnterpriseEstablishment> existingEnterpriseEstablishment = enterpriseEstablishmentRepository.listAll();

        if (existingEnterpriseEstablishment.isEmpty()){
            throw new NoContentException();
        }else{
            return existingEnterpriseEstablishment;
        }
    }

    public EnterpriseEstablishment getEnterpriseEstablishmentById(Long idEnterpriseEstablishment) throws NoContentException {
        EnterpriseEstablishment existingEnterpriseEstablishment = enterpriseEstablishmentRepository.findById(idEnterpriseEstablishment);

        if (existingEnterpriseEstablishment == null) {
            throw new NoContentException();
        }
        return existingEnterpriseEstablishment;
    }

    public EnterpriseEstablishment updateEnterpriseEstablishment(Long idEnterpriseEstablishment, EnterpriseEstablishment enterpriseEstablishment) throws NoContentException {
        EnterpriseEstablishment existingEnterpriseEstablishment = enterpriseEstablishmentRepository.findById(idEnterpriseEstablishment);

        if (existingEnterpriseEstablishment == null) {
            throw new NoContentException();
        } else {
            existingEnterpriseEstablishment.setStatus(enterpriseEstablishment.getStatus());
            enterpriseEstablishmentRepository.persist(existingEnterpriseEstablishment);
        }
    return existingEnterpriseEstablishment;
    }


    public EnterpriseEstablishment addEnterpriseEstablishment(EnterpriseEstablishmentRequest enterpriseEstablishmentRequest) throws NoContentException, BusinessException {

        Long idEnterprise = enterpriseEstablishmentRequest.idEnterprise();
        Long idEstablishment = enterpriseEstablishmentRequest.idEstablishment();

        Enterprise enterprise = enterpriseService.getEnterpriseById(idEnterprise);
        if (enterprise == null) {
            throw new NoContentException();
        }

        Establishment establishment = establishmentService.getEstablishmentById(idEstablishment);
        if (establishment == null) {
            throw new NoContentException();
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

    @Transactional
    public void removeEnterpriseEstablishment(Long idEnterpriseEstablishment) throws NoContentException {

        EnterpriseEstablishment existingEnterpriseEstablishment = enterpriseEstablishmentRepository.findById(idEnterpriseEstablishment);

        if (existingEnterpriseEstablishment == null) {
            throw new NoContentException();
        } else {
            enterpriseEstablishmentRepository.delete(existingEnterpriseEstablishment);
        }
    }
}
