package br.com.lucasvicente.contabancaria.controller;

import br.com.lucasvicente.contabancaria.entites.Account;
import br.com.lucasvicente.contabancaria.entites.PixKey;
import br.com.lucasvicente.contabancaria.service.PixKeyService;

import java.util.List;

public class PixKeyController {

    private final PixKeyService service = new PixKeyService();

    public List<PixKey> findAll() {
        return service.findAll();
    }

    public PixKey findById(long id) {
        return service.findById(id);
    }

    public PixKey insert (String keyValue, Account account) {
        PixKey pixKey = new PixKey();
        pixKey.setKeyValue(keyValue);
        pixKey.setAccount(account);
        return service.insert(pixKey);
    }

    public void update (long id, String keyValue) {
        PixKey pixKey = new PixKey();
        pixKey.setId(id);
        pixKey.setKeyValue(keyValue);
        service.update(pixKey);
    }

    public void delete(long id) {
        service.delete(id);
    }

    public List<PixKey> findAllByAccountId(Long id) {
        return service.findAllByAccountId(id);
    }
}
