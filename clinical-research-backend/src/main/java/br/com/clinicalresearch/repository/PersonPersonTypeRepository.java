package br.com.clinicalresearch.repository;

import br.com.clinicalresearch.domain.PersonPersonType;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonPersonTypeRepository implements PanacheRepository<PersonPersonType> {

    public PersonPersonType findByIdPersonAndIdPersonType(Long idPerson, Long idPersonType) {
        return find("person.id = ?1 and personType.id = ?2", idPerson, idPersonType).firstResult();
    }
}
