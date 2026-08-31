package br.com.lucasvicente.contabancaria.service;

import br.com.lucasvicente.contabancaria.dao.AccountDao;
import br.com.lucasvicente.contabancaria.dao.PersonDao;
import br.com.lucasvicente.contabancaria.dao.PixKeyDao;
import br.com.lucasvicente.contabancaria.dto.PersonResumeDTO;
import br.com.lucasvicente.contabancaria.dto.PixKeyResumeDTO;
import br.com.lucasvicente.contabancaria.dto.requests.AccountRequestDTO;
import br.com.lucasvicente.contabancaria.dto.responses.AccountResponseDTO;
import br.com.lucasvicente.contabancaria.entites.Account;
import br.com.lucasvicente.contabancaria.entites.Person;
import br.com.lucasvicente.contabancaria.entites.PixKey;
import br.com.lucasvicente.contabancaria.exceptions.InsufficientBalanceException;
import br.com.lucasvicente.contabancaria.exceptions.NegativeValueException;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {
    private final AccountDao accountDao = new AccountDao();
    private final PersonDao personDao = new PersonDao();
    private final PixKeyDao pixKeyDao = new PixKeyDao();

    public List<AccountResponseDTO> findAll() {
        return accountDao.findAll().stream().map(this::toDTO).toList();
    }

    public AccountResponseDTO findById(Long id) {
        return toDTO(accountDao.findById(id));
    }

    public AccountResponseDTO insert(AccountRequestDTO dto) {

        Person person = personDao.findById(dto.personId());

        Account account = new Account();
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

        List<PixKey> pixKeys = pixKeyDao.findAllByAccountId(account.getId());

        List<PixKeyResumeDTO> pixKeyDTOs = pixKeys.stream()
                .map(pk -> new PixKeyResumeDTO(pk.getId(), pk.getKeyValue()))
                .toList();

        return new AccountResponseDTO(
                account.getId(),
                new PersonResumeDTO(
                        account.getPerson().getId(),
                        account.getPerson().getFullName()
                ),
                account.getBalance(),
                account.getAccountNumber(),
                account.getAgency(),
                pixKeyDTOs
        );
    }
}
