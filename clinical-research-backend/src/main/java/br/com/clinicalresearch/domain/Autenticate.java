package br.com.clinicalresearch.domain;

import br.com.clinicalresearch.collection.StatusObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "AUTENTICATE")
@JsonPropertyOrder({"id", "cpf", "email", "password", "status", "createDate", "updateDate"})
public class Autenticate {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Column(name = "CPF")
    private String cpf;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PASSWORD_DECODER")
    private String passwordDecodificado;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private StatusObject status = StatusObject.PENDING;

    @Column(name = "CREATE_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "UPDATE_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    LocalDateTime updateDate = LocalDateTime.now();

    @OneToMany(mappedBy = "autenticate", cascade = CascadeType.ALL)
    private List<AutenticateToken> autenticateTokens;

    @OneToMany(mappedBy = "autenticate", cascade = CascadeType.ALL)
    private List<AutenticateEstablishment> autenticateEstablishments;

    @OneToOne
    private Person person;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordDecodificado() {
        return passwordDecodificado;
    }

    public void setPasswordDecodificado(String passwordDecodificado) {
        this.passwordDecodificado = passwordDecodificado;
    }

    public List<AutenticateEstablishment> getAutenticateEstablishments() {
        return autenticateEstablishments;
    }

    public void setAutenticateEstablishments(List<AutenticateEstablishment> autenticateEstablishments) {
        this.autenticateEstablishments = autenticateEstablishments;
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

    public List<AutenticateToken> getAutenticateTokens() {
        return autenticateTokens;
    }

    public void setAutenticateTokens(List<AutenticateToken> autenticateTokens) {
        this.autenticateTokens = autenticateTokens;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autenticate that = (Autenticate) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
