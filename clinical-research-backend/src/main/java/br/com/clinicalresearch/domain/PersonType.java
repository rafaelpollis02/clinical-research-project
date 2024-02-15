package br.com.clinicalresearch.domain;

import br.com.clinicalresearch.collection.StatusObject;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;

@Entity
@JsonbPropertyOrder({"id", "type", "status", "createDate", "updateDate"})
public class PersonType {

    @Id
    @GeneratedValue
    @Schema(description = "ID do Tipo de Pessoa", example = "1")
    private Long id;
    @NotBlank
    @Schema(description = "Tipo de Pessoa", example = "ADMINISTRATOR")
    private String type;

    private StatusObject status = StatusObject.ACTIVE;

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    private LocalDateTime createDate = LocalDateTime.now();

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    private LocalDateTime updateDate = LocalDateTime.now();

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

    public StatusObject getStatus() {
        return status;
    }

    public void setStatus(StatusObject status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
