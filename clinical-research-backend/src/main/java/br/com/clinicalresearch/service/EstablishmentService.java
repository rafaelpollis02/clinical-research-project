package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.exceptions.BusinessException;
import br.com.clinicalresearch.exceptions.NotFoundException;
import br.com.clinicalresearch.repository.EstablishmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class EstablishmentService {

    @Inject
    EstablishmentRepository establishmentRepository;

    public List<Establishment> getAllEstablishment() {
        return establishmentRepository.listAll();
    }

    public Establishment getEstablishmentById(Long idEstablishment) throws BusinessException {
        Establishment existingEstablishment = establishmentRepository.findById(idEstablishment);

        if (existingEstablishment == null) {
            throw new BusinessException("Establishment not registered with the ID " + idEstablishment);
        }
        return existingEstablishment;
    }

    public Establishment createEstablishment(@Valid Establishment establishment) {
        establishmentRepository.persist(establishment);
        return establishment;
    }

    public Establishment updateEstablishment(Long idEstablishment, Establishment establishment) throws BusinessException {

        Establishment existingEstablishment = establishmentRepository.findById(idEstablishment);

        if (existingEstablishment == null) {
            throw new BusinessException("Establishment not registered with the ID " + idEstablishment);
        } else {
            existingEstablishment.setName(establishment.getName());
            existingEstablishment.setLogoFile(establishment.getLogoFile());
            existingEstablishment.setStatus(establishment.getStatus());
            existingEstablishment.setUpdateDate(LocalDateTime.now());
            establishmentRepository.persist(existingEstablishment);
        }
        return existingEstablishment;
    }
    public void deleteEstablishment(Long idEstablishment) throws NotFoundException {
        Establishment existingEstablishment = establishmentRepository.findById(idEstablishment);

        if (existingEstablishment == null) {
            throw new NotFoundException("Establishment not registered with the ID " + idEstablishment);
        } else {
            establishmentRepository.delete(existingEstablishment);
        }
    }

}

