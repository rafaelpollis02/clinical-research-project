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

    public Enterprise getEnterpriseById(Long idEnterprise) throws BusinessException {
        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);

        if (existingEnterprise == null) {
            throw new BusinessException("Enterprise not registered with the ID " + idEnterprise);
        }

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

    public Enterprise addEstablishment(Long idEnterprise, Establishment establishment) throws BusinessException {

        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);
        Long idEstablishment = establishment.getId();
        Establishment existingEstablishment = establishmentRepository.findById(idEstablishment);


        if (existingEnterprise == null) {
            throw new BusinessException("Enterprise not registered with the ID " + idEnterprise);
        }

        if (existingEstablishment == null) {
            throw new BusinessException("Establisment not registered with the ID " + idEstablishment);
        } else {

            existingEnterprise.getEstablishment().add(establishment);
            enterpriseRepository.persist(existingEnterprise);
        }
        return existingEnterprise;
    }

    public Enterprise removeEstablishment(Long idEnterprise, Establishment establishment) throws BusinessException {

        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);
        Long idEstablishment = establishment.getId();

        if (existingEnterprise == null) {
            throw new BusinessException("Enterprise not registered with the ID " + idEnterprise);
        }

        if (!existingEnterprise.getEstablishment().removeIf(estab -> estab.getId().equals(idEstablishment))) {
            throw new BusinessException("Establishment with ID " + idEstablishment + " is not associated with the enterprise.");
        }

        enterpriseRepository.persist(existingEnterprise);
        return existingEnterprise;
    }


    public Enterprise updateEnterprise(Long idEnterprise, Enterprise enterprise) throws BusinessException {
        Enterprise existingEnterprise = enterpriseRepository.findById(idEnterprise);

        if (existingEnterprise == null) {
            throw new BusinessException("Enterprise not registered with the ID " + idEnterprise);
        } else {
            existingEnterprise.setCnpj(enterprise.getCnpj());
            existingEnterprise.setName(enterprise.getName());
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

}
