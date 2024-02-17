package br.com.clinicalresearch.domain;

import br.com.clinicalresearch.collection.StatusObject;
import br.com.clinicalresearch.relational.EnterpriseEstablishment;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonbPropertyOrder({"id", "cnpj", "name", "status","createDate","updateDate","establishment"})
public class Enterprise {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String cnpj;

    StatusObject status = StatusObject.ACTIVE;

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    LocalDateTime createDate = LocalDateTime.now();

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    LocalDateTime updateDate = LocalDateTime.now();

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonbTransient
    private List<EnterpriseEstablishment> enterpriseEstablishments = new ArrayList<>();


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

    public List<EnterpriseEstablishment> getEnterpriseEstablishments() {
        return enterpriseEstablishments;
    }

    public void setEnterpriseEstablishments(List<EnterpriseEstablishment> enterpriseEstablishments) {
        this.enterpriseEstablishments = enterpriseEstablishments;
    }

    @JsonbProperty("establishments")
    public List<Establishment> getEstablishments() {
        List<Establishment> establishments = new ArrayList<>();
        for (EnterpriseEstablishment ee : enterpriseEstablishments) {
            establishments.add(ee.getEstablishment());
        }
        return establishments;
    }

}
