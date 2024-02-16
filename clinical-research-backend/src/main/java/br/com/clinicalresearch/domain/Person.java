package br.com.clinicalresearch.domain;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@JsonbPropertyOrder({"id", "fullName", "cpf", "rg", "phoneNumber", "email", "birthDate", "createDate", "updateDate", "establishment", "personType"})
public class Person {

    @Id
    @GeneratedValue
    @Schema(description = "ID da pessoa", example = "1")
    private Long id;
    @NotBlank
    @Schema(description = "Nome completo da pessoa", example = "João da Silva")
    private String fullName;
    @NotBlank
    @Schema(description = "CPF da pessoa", example = "12345678900")
    private String cpf;
    @NotBlank
    @Schema(description = "RG da pessoa", example = "45938094983")
    private String rg;
    @NotBlank
    @Schema(description = "Celular da pessoa", example = "11964537847")
    private String phoneNumber;
    @Email
    @NotBlank
    @Schema(description = "Email da pessoa", example = "teste@gmail.com")
    private String email;

    @JsonbDateFormat("dd/MM/yyyy")
    @Schema(description = "Data de Nascimento da pessoa", example = "01/01/2000")
    private LocalDate birthDate;

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    LocalDateTime createDate = LocalDateTime.now();

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    LocalDateTime updateDate = LocalDateTime.now();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_persontype",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "person_type_id")
    )
    private List<PersonType> personType;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_establishment",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "establishment_id")
    )
    private List<Establishment> establishment;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Autenticate autenticate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public List<PersonType> getPersonType() {
        return personType;
    }

    public void setPersonType(List<PersonType> personType) {
        this.personType = personType;
    }

    public List<Establishment> getEstablishment() {
        return establishment;
    }

    public void setEstablishment(List<Establishment> establishment) {
        this.establishment = establishment;
    }

    @JsonbTransient
    public Autenticate getAutenticate() {
        return autenticate;
    }

    public void setAutenticate(Autenticate autenticate) {
        this.autenticate = autenticate;
    }
}
