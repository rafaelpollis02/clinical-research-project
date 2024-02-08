package br.com.clinicalresearch.domain;

import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Entity
@JsonbPropertyOrder({"idPersonType", "name"})
public class PersonType {

    @Id
    @GeneratedValue
    @Schema(description = "ID do Tipo de Pessoa", example = "1")
    private Long id;
    @NotBlank
    @Schema(description = "Tipo de Pessoa", example = "ADMINISTRATOR")
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
