package br.com.clinicalresearch.domain;

import br.com.clinicalresearch.collection.StatusObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ENTERPRISE")
@JsonPropertyOrder({"id", "name", "description", "cnpj", "status", "createDate", "updateDate"})
public class Enterprise {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "CNPJ")
    private String cnpj;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private StatusObject status = StatusObject.ACTIVE;
    @Column(name = "CREATE_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    @JsonIgnore
    private LocalDateTime createDate = LocalDateTime.now();
    @Column(name = "UPDATE_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    @JsonIgnore
    private LocalDateTime updateDate = LocalDateTime.now();

    @OneToMany(mappedBy = "enterprise", fetch = FetchType.EAGER)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enterprise that = (Enterprise) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
