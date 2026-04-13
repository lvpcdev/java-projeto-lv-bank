package br.com.lucasvicente.contabancaria.entites;

public class PixKey {
    private Long id;
    private String keyValue;
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
