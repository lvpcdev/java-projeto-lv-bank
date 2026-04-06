package br.com.lucasvicente.contabancaria.entites;


import java.util.ArrayList;
import java.util.List;

public class Person {
    private Long id;
    private String fullName;
    private String cpf;
    private List<Account> accounts = new ArrayList<>();

    public Person(){

    }

    public Person(Long id, String fullName, String cpf) {
        this.id = id;
        this.fullName = fullName;
        this.cpf = cpf;
    }




    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount (Account account) {
        this.accounts.add(account);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}