package br.com.clinicalresearch.domain;

import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonbPropertyOrder({"idPersonType", "name"})
public class PersonType {

    @Id
    @GeneratedValue
    private Long id;
    private String type;

    @ManyToMany
    private List<Person> person;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
