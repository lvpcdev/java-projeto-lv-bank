package br.com.lucasvicente.contabancaria.entites;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {

    private Long id;
    private Person person;
    private String password;
    private BigDecimal balance;
    private Integer accountNumber;
    private String agency;

    private List<PixKey> pixKeys = new ArrayList<>();

    public Account() {
        this.balance = new BigDecimal("0.0");
    }

    public Account(Long id, Person person, String password, Integer accountNumber, String agency) {
        this.id = id;
        this.person = person;
        this.password = password;
        this.accountNumber = accountNumber;
        this.agency = agency;
        this.balance = new BigDecimal("0.0");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<PixKey> getPixKeys() {
        return pixKeys;
    }


    public void addPixKey (PixKey pixKey) {
        this.pixKeys.add(pixKey);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void depostit(BigDecimal balance) {
        this.balance = this.balance.add(balance);
    }

    public void withdraw(BigDecimal balance) {
        this.balance = this.balance.subtract(balance);
    }
}
