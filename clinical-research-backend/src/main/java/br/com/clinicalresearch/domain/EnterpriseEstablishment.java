package br.com.clinicalresearch.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ENTERPRISE_ESTABLISHMENT")
@JsonPropertyOrder({"id", "createDate", "enterprise", "establishment"})
public class EnterpriseEstablishment {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    @JsonIgnore
    private LocalDateTime createDate = LocalDateTime.now();
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
    @ManyToOne
    @JoinColumn(name = "establishment_id")
    private Establishment establishment;

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