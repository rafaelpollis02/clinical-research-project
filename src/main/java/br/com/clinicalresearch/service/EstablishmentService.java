package br.com.clinicalresearch.service;


import br.com.clinicalresearch.domain.Establishment;
import br.com.clinicalresearch.repository.EstablishmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class EstablishmentService {

    @Inject
    EstablishmentRepository establishmentRepository;

    public List<Establishment> getAllEstablishment() {
        return establishmentRepository.listAll();
    }

    public Establishment saveEstablishment(Establishment establishment) {
        establishmentRepository.persist(establishment);
        return establishment;
    }
}
