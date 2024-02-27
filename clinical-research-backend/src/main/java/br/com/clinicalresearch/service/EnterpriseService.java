package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Enterprise;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NoContentException;
import br.com.clinicalresearch.repository.EnterpriseRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class EnterpriseService {

    @Inject
    EnterpriseRepository enterpriseRepository;

    public List<Enterprise> getAllEnterprise() throws NoContentException {
        List<Enterprise> existingEnterprise = enterpriseRepository.listAll();

        if (existingEnterprise.isEmpty()){
            throw new NoContentException();
        }else{
            return existingEnterprise;
        }
    }

    public Enterprise getEnterpriseById(Long idEnterprise) throws NoContentException {
        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);

        if (existingEnterprise == null) {
            throw new NoContentException();
        }
        return existingEnterprise;
    }

    public List<Enterprise> getEnterpriseByName(String name) throws NoContentException {
        List<Enterprise> existingEnterprise = enterpriseRepository.findByName(name);

        if (existingEnterprise.isEmpty()) {
            throw new NoContentException();
        }
        return existingEnterprise;
    }

    public Enterprise createEnterprise(Enterprise enterprise) throws BusinessException {
        Enterprise existingEnterprise = enterpriseRepository.findByCnpj(enterprise.getCnpj());
        if (existingEnterprise != null) {
            throw new BusinessException("Enterprise duplicate by cnpj " + enterprise.getCnpj());
        } else {
            enterpriseRepository.persist(enterprise);
        }
        return enterprise;
    }

    public Enterprise updateEnterprise(Long idEnterprise, Enterprise enterprise) throws NoContentException {
        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);

        if (existingEnterprise == null) {
            throw new NoContentException();
        } else {
            existingEnterprise.setCnpj(enterprise.getCnpj());
            existingEnterprise.setName(enterprise.getName());
            existingEnterprise.setStatus(enterprise.getStatus());
            existingEnterprise.setUpdateDate(LocalDateTime.now());
            enterpriseRepository.persist(existingEnterprise);
        }
        return existingEnterprise;
    }

    public void deleteEnterprise(Long idEnterprise) throws NoContentException {
        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);

        if (existingEnterprise == null) {
            throw new NoContentException();
        } else {
            enterpriseRepository.delete(existingEnterprise);
        }
    }

}