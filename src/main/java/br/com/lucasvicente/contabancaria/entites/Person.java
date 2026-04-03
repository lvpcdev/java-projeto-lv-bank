package br.com.lucasvicente.contabancaria.entites;


import java.util.ArrayList;
import java.util.List;

public class Person {
    private String fullName;
    private String cpf;
    private List<Account> accounts = new ArrayList<>();

    public Person(String fullName, String cpf) {
        this.fullName = fullName;
        this.cpf = cpf;
    }

    public Person(){

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
}