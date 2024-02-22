package br.com.clinicalresearch.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "autenticate_establishment")
public class AutenticateEstablishment {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "autenticate_id")
    private Autenticate autenticate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "establishment_id")
    private Establishment establishment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Autenticate getAutenticate() {
        return autenticate;
    }

    public void setAutenticate(Autenticate autenticate) {
        this.autenticate = autenticate;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutenticateEstablishment that = (AutenticateEstablishment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
