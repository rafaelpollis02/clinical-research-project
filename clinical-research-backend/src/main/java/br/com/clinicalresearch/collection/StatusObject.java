package br.com.clinicalresearch.collection;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum StatusObject {

    ACTIVE("A"),
    PENDING("P"),
    INACTIVE("I");

    private final String status;

    StatusObject(String status) {
        this.status = status;
    }
}
