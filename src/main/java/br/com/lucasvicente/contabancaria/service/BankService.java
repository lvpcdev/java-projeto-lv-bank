package br.com.lucasvicente.contabancaria.service;

import br.com.lucasvicente.contabancaria.dao.BankDao;
import br.com.lucasvicente.contabancaria.entites.Bank;

import java.util.List;

public class BankService {
    private final BankDao dao = new BankDao();

    public List<Bank> findAll() {
        return dao.findAll();
    }

    public Bank findById(long id) {
        return dao.findById(id);
    }

    public void insert(Bank bank) {
        dao.insert(bank);
    }

    public void delete(long id) {
        dao.deleteById(id);
    }

    public void update(Bank bank) {
        dao.update(bank);
    }
}
