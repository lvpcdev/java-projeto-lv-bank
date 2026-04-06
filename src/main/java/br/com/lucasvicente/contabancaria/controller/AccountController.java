package br.com.lucasvicente.contabancaria.controller;

import br.com.lucasvicente.contabancaria.entites.Account;
import br.com.lucasvicente.contabancaria.entites.Bank;
import br.com.lucasvicente.contabancaria.entites.Person;
import br.com.lucasvicente.contabancaria.service.AccountService;

import java.util.List;

public class AccountController {
    private final AccountService service = new AccountService();

    public List<Account> findAll() {
        return service.findAll();
    }

    public Account findById(long id) {
        return service.findById(id);
    }

    public Account insert (Bank bank, Person person, String password, int accountNumber, String agency) {
        Account account = new Account();
        account.setBank(bank);
        account.setPerson(person);
        account.setPassword(password);
        account.setAccountNumber(accountNumber);
        account.setAgency(agency);

        return service.insert(account);
    }

    public void update (long id, String password) {
        Account account = new Account();
        account.setId(id);
        account.setPassword(password);
        service.update(account);
    }

    public void delete(long id) {
        service.delete(id);
    }
}
