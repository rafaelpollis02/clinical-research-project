package br.com.clinicalresearch.exceptions;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"type", "message"})
public class Message {

    String message;
    String type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
