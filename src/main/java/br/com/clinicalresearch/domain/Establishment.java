package br.com.clinicalresearch.domain;

import br.com.clinicalresearch.collection.StatusObject;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@JsonbPropertyOrder({"id", "name", "status", "createDate", "updateDate", "logoFile"})
public class Establishment {

    @Id
    @GeneratedValue
    Long id;
    String name;
    StatusObject status = StatusObject.ACTIVE;

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    LocalDateTime createDate = LocalDateTime.now();
    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    LocalDateTime updateDate = LocalDateTime.now();

    String logoFile;

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

    public String getLogoFile() {
        return logoFile;
    }

    public void setLogoFile(String logoFile) {
        this.logoFile = logoFile;
    }

}
