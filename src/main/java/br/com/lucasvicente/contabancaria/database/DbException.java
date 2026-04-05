package br.com.lucasvicente.contabancaria.database;

public class DbException extends RuntimeException {
    public DbException(String message) {
        super(message);
    }
}
