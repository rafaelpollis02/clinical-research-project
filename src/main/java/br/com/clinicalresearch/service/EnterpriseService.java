package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Enterprise;
import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.repository.EnterpriseRepository;
import br.com.clinicalresearch.repository.EstablishmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EnterpriseService {

    @Inject
    EnterpriseRepository enterpriseRepository;

    @Inject
    EstablishmentRepository establishmentRepository;

    public List<Enterprise> getAllEnterprise() {
        return enterpriseRepository.listAll();
    }

    public Enterprise getEnterpriseById(Long idEnterprise) {
        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);
        return existingEnterprise;
    }

    public Enterprise saveEnterprise(Enterprise enterprise) throws BusinessException {
        Optional<Enterprise> existingEnterprise = enterpriseRepository.findEnterpriseByCnpj(enterprise.getCnpj());
        if (existingEnterprise.isPresent()) {
            throw new BusinessException("Enterprise duplicate by cnpj " + enterprise.getCnpj());
        } else {
            enterpriseRepository.persist(enterprise);
        }
        return enterprise;
    }

    public Enterprise addEstablishment(Long idEnterprise, Establishment establishment){

        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);
        Establishment existingEstablishment = establishmentRepository.findById(idEnterprise);
        Long idEstablishment = establishment.getId();

        existingEnterprise.getEstablishment().add(establishment);
        enterpriseRepository.persist(existingEnterprise);

        return existingEnterprise;
    }

    public Enterprise updateEnterprise(Long idEnterprise, Enterprise enterprise) {
        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);

        existingEnterprise.setCnpj(enterprise.getCnpj());
        existingEnterprise.setName(enterprise.getName());

        enterpriseRepository.persist(existingEnterprise);

        return enterprise;
    }

    public void deleteEnterprise(Long idEnterprise) {
        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);
        enterpriseRepository.delete(existingEnterprise);
    }

}
