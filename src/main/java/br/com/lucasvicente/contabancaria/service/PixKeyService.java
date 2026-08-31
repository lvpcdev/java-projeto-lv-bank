package br.com.lucasvicente.contabancaria.service;

import br.com.lucasvicente.contabancaria.dao.AccountDao;
import br.com.lucasvicente.contabancaria.dao.PersonDao;
import br.com.lucasvicente.contabancaria.dao.PixKeyDao;
import br.com.lucasvicente.contabancaria.dto.AccountResumeDTO;
import br.com.lucasvicente.contabancaria.dto.PersonResumeDTO;
import br.com.lucasvicente.contabancaria.dto.requests.AccountRequestDTO;
import br.com.lucasvicente.contabancaria.dto.requests.PixKeyRequestDTO;
import br.com.lucasvicente.contabancaria.dto.responses.PixKeyResponseDTO;
import br.com.lucasvicente.contabancaria.entites.Account;
import br.com.lucasvicente.contabancaria.entites.Person;
import br.com.lucasvicente.contabancaria.entites.PixKey;

import java.util.List;

public class PixKeyService {
    private final PixKeyDao pixKeyDao = new PixKeyDao();
    private final PersonDao personDao = new PersonDao();
    private final AccountDao accountDao = new AccountDao();

    public List<PixKeyResponseDTO> findAll() {
        return pixKeyDao.findAll().stream().map(this::toDTO).toList();
    }

    public PixKeyResponseDTO findById(Long id) {
        return toDTO(pixKeyDao.findById(id));
    }

    public PixKeyResponseDTO insert(PixKeyRequestDTO dto) {

        Account account = accountDao.findById(dto.accountId());

        PixKey pixKey = new PixKey();

        pixKey.setKeyValue(dto.keyValue());
        pixKey.setAccount(account);

        return toDTO(pixKeyDao.insert(pixKey));

    }

    public void delete(Long id) {
        pixKeyDao.deleteById(id);
    }

    public PixKeyResponseDTO update(Long id, PixKeyRequestDTO dto) {
        PixKey existingPixKey = pixKeyDao.findById(id);

        if (existingPixKey == null) {
            throw new IllegalArgumentException("chave pix não encontrada");
        }

        existingPixKey.setKeyValue(dto.keyValue());

        return toDTO(pixKeyDao.update(existingPixKey));
    }

    public List<PixKeyResponseDTO> findAllByAccountId(Long accountId) {
        return pixKeyDao.findAllByAccountId(accountId).stream().map(this::toDTO).toList();
    }

    public PixKeyResponseDTO toDTO(PixKey pixKey) {
        return new PixKeyResponseDTO(
                pixKey.getId(),
                pixKey.getKeyValue(),
                new AccountResumeDTO(
                        pixKey.getAccount().getId(),
                        new PersonResumeDTO(
                                pixKey.getAccount().getPerson().getId(),
                                pixKey.getAccount().getPerson().getFullName()
                        )
                )
        );
    }
}
