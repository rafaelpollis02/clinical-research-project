package br.com.clinicalresearch.domain;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class AutenticateToken {

    @Id
    @GeneratedValue
    private Long id;
    private int token;

    private Long idAutenticate;

    @JsonbDateFormat("dd/MM/yy hh:MM:ss")
    private LocalDateTime createDate = LocalDateTime.now();

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

    public Long getIdAutenticate() {
        return idAutenticate;
    }

    public void setIdAutenticate(Long idAutenticate) {
        this.idAutenticate = idAutenticate;
    }
}
