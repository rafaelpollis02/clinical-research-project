package br.com.clinicalresearch.exceptions;

    public class InvalidLoginException extends Exception {
        public InvalidLoginException(String message) {
            super(message);
        }
    }