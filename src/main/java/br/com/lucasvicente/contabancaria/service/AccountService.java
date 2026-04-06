package br.com.lucasvicente.contabancaria.service;

import br.com.lucasvicente.contabancaria.dao.AccountDao;
import br.com.lucasvicente.contabancaria.entites.Account;

import java.util.List;

public class AccountService {
    private final AccountDao dao = new AccountDao();

    public List<Account> findAll() {
        return dao.findAll();
    }

    public Account findById(long id) {
        return dao.findById(id);
    }

    public Account insert(Account account) {
        return dao.insert(account);
    }

    public void delete(long id) {
        dao.deleteById(id);
    }

    public void update(Account account) {
        dao.update(account);
    }
}
