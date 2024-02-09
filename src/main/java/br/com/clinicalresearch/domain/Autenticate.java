package br.com.clinicalresearch.domain;

import br.com.clinicalresearch.collection.StatusObject;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@JsonbPropertyOrder({"id", "cpf", "email", "status", "createDate", "updateDate"})
public class Autenticate {

    @Id
    @GeneratedValue
    private Long id;
    private String cpf;
    private String email;
    private boolean firstAcess = true;

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    LocalDateTime createDate = LocalDateTime.now();

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    LocalDateTime updateDate = LocalDateTime.now();


    private StatusObject status = StatusObject.ACTIVE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StatusObject getStatus() {
        return status;
    }

    public void setStatus(StatusObject status) {
        this.status = status;
    }

    public boolean isFirstAcess() {
        return firstAcess;
    }

    public void setFirstAcess(boolean firstAcess) {
        this.firstAcess = firstAcess;
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
