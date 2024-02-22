package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Enterprise;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NotFoundException;
import br.com.clinicalresearch.repository.EnterpriseRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class EnterpriseService {

    @Inject
    EnterpriseRepository enterpriseRepository;

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
        Enterprise existingEnterprise = enterpriseRepository.findEnterpriseByCnpj(enterprise.getCnpj());
        if (existingEnterprise != null) {
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

    public void deleteEnterprise(Long idEnterprise) throws NotFoundException {
        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);

        if (existingEnterprise == null) {
            throw new NotFoundException("Enterprise not registered with the ID " + idEnterprise);
        } else {
            enterpriseRepository.delete(existingEnterprise);
        }
    }

}