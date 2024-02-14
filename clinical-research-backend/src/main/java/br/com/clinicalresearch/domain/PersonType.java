package br.com.clinicalresearch.domain;

import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

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
