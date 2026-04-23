package br.com.lucasvicente.contabancaria.service;

import br.com.lucasvicente.contabancaria.dao.AccountDao;
import br.com.lucasvicente.contabancaria.entites.Account;
import br.com.lucasvicente.contabancaria.exceptions.InsufficientBalanceException;
import br.com.lucasvicente.contabancaria.exceptions.NegativeValueException;

import java.math.BigDecimal;
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

    public void deposit(Long accountId, BigDecimal value) throws NegativeValueException {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeValueException("Valor inválido");
        }
        dao.deposit(accountId, value);
    }

    public void withdraw(Long accountId, BigDecimal value) throws NegativeValueException, InsufficientBalanceException {
        int comparator;
        comparator = value.compareTo(findById(accountId).getBalance());
        if (comparator > 0) {
            throw new InsufficientBalanceException("Valor de saque maior do que valor disponivel");

        } else if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeValueException("Valor não pode ser negativo");
        } else {
            dao.withdraw(accountId, value);
        }
    }


}
