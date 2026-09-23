package br.com.lucasvicente.contabancaria.service;

import br.com.lucasvicente.contabancaria.repository.*;
import br.com.lucasvicente.contabancaria.dto.PixKeyDTO.PixKeyResumeDTO;
import br.com.lucasvicente.contabancaria.dto.PersonDTO.PersonResumeDTO;
import br.com.lucasvicente.contabancaria.dto.AccountDTO.AccountResponseDTO;
import br.com.lucasvicente.contabancaria.dto.AccountDTO.AccountRequestDTO;
import br.com.lucasvicente.contabancaria.entites.Account;
import br.com.lucasvicente.contabancaria.entites.Person;
import br.com.lucasvicente.contabancaria.entites.PixKey;
import br.com.lucasvicente.contabancaria.exceptions.InsufficientBalanceException;
import br.com.lucasvicente.contabancaria.exceptions.NegativeValueException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PersonRepository personRepository;
    private final PixKeyRepository pixKeyRepository;

    public AccountService(AccountRepository accountRepository, PersonRepository personRepository, PixKeyRepository pixKeyRepository) {
        this.accountRepository = accountRepository;
        this.personRepository = personRepository;
        this.pixKeyRepository = pixKeyRepository;
    }

    public List<AccountResponseDTO> findAll() {
        return accountRepository.findAll().stream().map(this::toDTO).toList();
    }

    public AccountResponseDTO findById(Long id) {
        Account existsAccount = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

        return toDTO(existsAccount);
    }

    public AccountResponseDTO insert(AccountRequestDTO dto) {

        Person existsPerson = personRepository.findById(dto.personId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada."));

        int min = 100000;
        int max = 999999;
        int generatedNumber;

        do{
            generatedNumber = ThreadLocalRandom.current().nextInt(min, max+1);
        } while (accountRepository.existsAccountByAccountNumber(generatedNumber));


        Account account = new Account();
        account.setPerson(existsPerson);
        account.setAccountNumber(generatedNumber);
        account.setAgency("0001");
        account.setBalance(BigDecimal.ZERO);


        return toDTO(accountRepository.save(account));
    }

    public void delete(Long id) {
        Account existsAccount = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

        accountRepository.deleteById(existsAccount.getId());
    }

    public void deposit(Long accountId, BigDecimal value) throws NegativeValueException {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeValueException("Valor inválido");
        }
        accountRepository.deposit(accountId, value);
    }

    public void withdraw(Long accountId, BigDecimal value) throws NegativeValueException, InsufficientBalanceException {
        int comparator;
        Account existsAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

        comparator = value.compareTo(existsAccount.getBalance());
        if (comparator > 0) {
            throw new InsufficientBalanceException("Valor de saque maior do que valor disponivel");

        } else if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeValueException("Valor não pode ser negativo");
        } else {
            accountRepository.withdraw(accountId, value);
        }
    }

    @Transactional
    public void sendPix(String receiverPixKey, Long issuerId, BigDecimal value) {
        Long receiverId = accountRepository.findAccountByPixKey(receiverPixKey);

        if (receiverId == null) {
            throw new IllegalArgumentException("Chave pix não encontrada");
        }

        withdraw(issuerId, value);

        deposit(receiverId, value);
    }

    private AccountResponseDTO toDTO(Account account) {

        List<PixKey> pixKeys = pixKeyRepository.findAllByAccountId(account.getId());

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
