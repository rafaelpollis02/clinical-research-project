package br.com.clinicalresearch.relational;

import br.com.clinicalresearch.domain.Enterprise;
import br.com.clinicalresearch.domain.Establishment;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@JsonbPropertyOrder({"id", "establishment", "createDate"})
@Table(name = "enterprise_establishment")
public class EnterpriseEstablishment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Enterprise enterprise;

    @ManyToOne
    private Establishment establishment;

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    private LocalDateTime createDate = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}