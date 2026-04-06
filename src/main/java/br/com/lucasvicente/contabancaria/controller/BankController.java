package br.com.lucasvicente.contabancaria.controller;

import br.com.lucasvicente.contabancaria.entites.Bank;
import br.com.lucasvicente.contabancaria.service.BankService;

import java.util.List;

public class BankController {

    private final BankService service = new BankService();

    public List<Bank> findAll() {
        return service.findAll();
    }

    public Bank findById(long id) {
        return service.findById(id);
    }

    public void insert (String name) {
        Bank bank = new Bank();
        bank.setName(name);
        service.insert(bank);
    }

    public void update (long id, String name) {
        Bank bank = new Bank();
        bank.setId(id);
        bank.setName(name);
        service.update(bank);
    }

    public void delete(long id) {
        service.delete(id);
    }
}
