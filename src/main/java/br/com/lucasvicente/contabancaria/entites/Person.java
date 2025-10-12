package br.com.lucasvicente.contabancaria.entites;

public class Person {
    private String fullName;
    private String cpf;
    private String password;

    // Getters
    public String getFullName() { return fullName; }
    public String getCpf() { return cpf; }
    public String getPassword() { return password; }

    // Setters
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setPassword(String password) { this.password = password; }
}