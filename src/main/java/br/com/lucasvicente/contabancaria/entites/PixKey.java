package br.com.lucasvicente.contabancaria.entites;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_pixkey")
public class PixKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String keyValue;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public PixKey() {
    }

    public PixKey(Long id, String keyValue, Account account) {
        this.id = id;
        this.keyValue = keyValue;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
