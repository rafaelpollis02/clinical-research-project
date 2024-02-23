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
@Table(name = "ESTABLISHMENT")
@JsonPropertyOrder({"id", "name", "logoFile", "status", "createDate", "updateDate"})
public class Establishment {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "LOGO_FILE")
    private String logoFile;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private StatusObject status = StatusObject.ACTIVE;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    @JsonIgnore
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate = LocalDateTime.now();


    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate = LocalDateTime.now();

    @OneToMany(mappedBy = "establishment", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<EnterpriseEstablishment> enterpriseEstablishments = new ArrayList<>();

    @OneToMany(mappedBy = "establishment", fetch = FetchType.EAGER)
    private List<AutenticateEstablishment> autenticateEstablishments = new ArrayList<>();

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
        Establishment that = (Establishment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
