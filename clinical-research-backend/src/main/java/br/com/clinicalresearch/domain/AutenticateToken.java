package br.com.clinicalresearch.domain;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDateTime;

@Entity
public class AutenticateToken {

    @Id
    @GeneratedValue
    private Long id;
    private int token;

    @ManyToOne
    private Autenticate autenticate;

    @JsonbDateFormat("dd/MM/yy hh:MM:ss")
    private LocalDateTime createDate = LocalDateTime.now();

    @JsonbDateFormat("dd/MM/yy hh:MM:ss")
    private LocalDateTime expireDate = LocalDateTime.now().plusMinutes(2);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public Autenticate getAutenticate() {
        return autenticate;
    }

    public void setAutenticate(Autenticate autenticate) {
        this.autenticate = autenticate;
    }
}
