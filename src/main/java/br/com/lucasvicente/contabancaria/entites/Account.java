package br.com.lucasvicente.contabancaria.entites;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {

    private Long id;
    private Bank bank;
    private Person person;
    private String password;
    private BigDecimal balance;
    private int accountNumber;
    private String agency;

    private List<String> pixKeys = new ArrayList<>();

    public Account() {
        this.balance = new BigDecimal("0.0");
    }

    public Account(Long id,Bank bank, Person person, String password, int accountNumber, String agency) {
        this.id = id;
        this.bank = bank;
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

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
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

    public List<String> getPixKeys() {
        return pixKeys;
    }


    public void addPixKey (String pixKey) {
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
