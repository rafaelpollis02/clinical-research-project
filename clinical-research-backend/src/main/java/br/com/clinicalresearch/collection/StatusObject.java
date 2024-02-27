package br.com.clinicalresearch.collection;

public enum StatusObject {

    ACTIVE("A"),
    PENDING("P"),
    INACTIVE("I");

    private final String status;

    StatusObject(String status) {
        this.status = status;
    }
}
