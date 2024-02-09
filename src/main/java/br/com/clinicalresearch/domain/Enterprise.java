package br.com.clinicalresearch.domain;

import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonbPropertyOrder({"id", "cnpj", "name", "establishment"})
public class Enterprise {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String cnpj;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "enterprise_establishment",
            joinColumns = @JoinColumn(name = "enterprise_id"),
            inverseJoinColumns = @JoinColumn(name = "establishment_id"))
    private List<Establishment> establishment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<Establishment> getEstablishment() {
        return establishment;
    }

    public void setEstablishment(List<Establishment> establishment) {
        this.establishment = establishment;
    }
}
