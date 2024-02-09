package br.com.clinicalresearch.domain;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;


@Entity
@JsonbPropertyOrder({"id", "fullName", "cpf", "rg", "phoneNumber", "email", "birthDate", "status", "establishment", "personType"})
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

    public List<PersonType> getPersonType() {
        return personType;
    }

    public void setPersonType(List<PersonType> personType) {
        this.personType = personType;
    }

    public List<Establishment> getEstablishment() {
        return establishment;
    }

    public void setEstablishmentLis(List<Establishment> establishment) {
        this.establishment = establishment;
    }
}
