package br.com.clinicalresearch.service;

import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.exceptions.NoContentException;
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

    public List<Establishment> getAllEstablishment() throws NoContentException {
        List<Establishment> existingEstablishmento = establishmentRepository.listAll();

        if(existingEstablishmento.isEmpty()){
            throw new NoContentException();
        }else{
            return existingEstablishmento;
        }
    }

    public Establishment getEstablishmentById(Long idEstablishment) throws NoContentException {
        Establishment existingEstablishment = establishmentRepository.findById(idEstablishment);

        if (existingEstablishment == null) {
            throw new NoContentException();
        }
        return existingEstablishment;
    }

    public List<Establishment> getEstablishmentByName(String name) throws NoContentException {
        List<Establishment> existingEstablishment = establishmentRepository.findByName(name);

        if (existingEstablishment.isEmpty()) {
            throw new NoContentException();
        }
        return existingEstablishment;
    }

    public Establishment createEstablishment(@Valid Establishment establishment) {
        establishmentRepository.persist(establishment);
        return establishment;
    }

    public Establishment updateEstablishment(Long idEstablishment, Establishment establishment) throws NoContentException {

        Establishment existingEstablishment = establishmentRepository.findById(idEstablishment);

        if (existingEstablishment == null) {
            throw new NoContentException();
        } else {
            existingEstablishment.setName(establishment.getName());
            existingEstablishment.setLogoFile(establishment.getLogoFile());
            existingEstablishment.setStatus(establishment.getStatus());
            existingEstablishment.setUpdateDate(LocalDateTime.now());
            establishmentRepository.persist(existingEstablishment);
        }
        return existingEstablishment;
    }

    public void deleteEstablishment(Long idEstablishment) throws NoContentException {
        Establishment existingEstablishment = establishmentRepository.findById(idEstablishment);

        if (existingEstablishment == null) {
            throw new NoContentException();
        } else {
            establishmentRepository.delete(existingEstablishment);
        }
    }

}

