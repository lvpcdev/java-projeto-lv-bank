package br.com.lucasvicente.contabancaria.entites;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    private BigDecimal balance;
    private Integer accountNumber;
    private String agency;

   @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PixKey> pixKeys = new ArrayList<>();

    public Account() {
        this.balance = new BigDecimal("0.0");
    }

    public Account(Long id, Person person, Integer accountNumber, String agency) {
        this.id = id;
        this.person = person;
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

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
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
