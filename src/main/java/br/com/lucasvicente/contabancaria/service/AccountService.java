package br.com.lucasvicente.contabancaria.service;

import br.com.lucasvicente.contabancaria.dao.AccountDao;
import br.com.lucasvicente.contabancaria.dao.BankDao;
import br.com.lucasvicente.contabancaria.dao.PersonDao;
import br.com.lucasvicente.contabancaria.dto.BankResumeDTO;
import br.com.lucasvicente.contabancaria.dto.PersonResumeDTO;
import br.com.lucasvicente.contabancaria.dto.requests.AccountRequestDTO;
import br.com.lucasvicente.contabancaria.dto.responses.AccountResponseDTO;
import br.com.lucasvicente.contabancaria.entites.Account;
import br.com.lucasvicente.contabancaria.entites.Bank;
import br.com.lucasvicente.contabancaria.entites.Person;
import br.com.lucasvicente.contabancaria.exceptions.InsufficientBalanceException;
import br.com.lucasvicente.contabancaria.exceptions.NegativeValueException;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {
    private final AccountDao accountDao = new AccountDao();
    private final BankDao bankDao = new BankDao();
    private final PersonDao personDao = new PersonDao();

    public List<AccountResponseDTO> findAll() {
        return accountDao.findAll().stream().map(this::toDTO).toList();
    }

    public AccountResponseDTO findById(long id) {
        return toDTO(accountDao.findById(id));
    }

    public AccountResponseDTO insert(AccountRequestDTO dto) {

        Bank bank = bankDao.findById(dto.bankId());
        Person person = personDao.findById(dto.personId());

        Account account = new Account();
        account.setBank(bank);
        account.setPerson(person);
        account.setAccountNumber(dto.accountNumber());
        account.setPassword(dto.password());
        account.setAgency(dto.agency());
        account.setBalance(BigDecimal.ZERO);


        return toDTO(accountDao.insert(account));
    }

    public void delete(long id) {
        accountDao.deleteById(id);
    }

    public AccountResponseDTO update(Long id, AccountRequestDTO dto) {
        Account existingAccount = accountDao.findById(id);
        if (existingAccount == null) {
            throw new IllegalArgumentException("conta não encontrada");
        }

        existingAccount.setPassword(dto.password());

        return toDTO(accountDao.update(existingAccount));
    }

    public void deposit(Long accountId, BigDecimal value) throws NegativeValueException {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeValueException("Valor inválido");
        }
        accountDao.deposit(accountId, value);
    }

    public void withdraw(Long accountId, BigDecimal value) throws NegativeValueException, InsufficientBalanceException {
        int comparator;
        comparator = value.compareTo(accountDao.findById(accountId).getBalance());
        if (comparator > 0) {
            throw new InsufficientBalanceException("Valor de saque maior do que valor disponivel");

        } else if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeValueException("Valor não pode ser negativo");
        } else {
            accountDao.withdraw(accountId, value);
        }
    }

    private AccountResponseDTO toDTO(Account account) {
        return new AccountResponseDTO(
                account.getId(),
                new BankResumeDTO(
                        account.getBank().getId(),
                        account.getBank().getName()
                ),
                new PersonResumeDTO(
                        account.getPerson().getId(),
                        account.getPerson().getFullName()
                ),
                account.getBalance(),
                account.getAccountNumber(),
                account.getAgency(),
                account.getPixKeys()
        );
    }


}
