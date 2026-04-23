package br.com.lucasvicente.contabancaria.exceptions;

public class NegativeValueException extends RuntimeException {
    public NegativeValueException(String message) {
        super(message);
    }
}
