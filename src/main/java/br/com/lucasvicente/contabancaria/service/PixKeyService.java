package br.com.lucasvicente.contabancaria.service;

import br.com.lucasvicente.contabancaria.dao.*;
import br.com.lucasvicente.contabancaria.dto.AccountDTO.AccountResumeDTO;
import br.com.lucasvicente.contabancaria.dto.PersonDTO.PersonResumeDTO;
import br.com.lucasvicente.contabancaria.dto.PixKeyDTO.PixKeyRequestDTO;
import br.com.lucasvicente.contabancaria.dto.PixKeyDTO.PixKeyResponseDTO;
import br.com.lucasvicente.contabancaria.entites.Account;
import br.com.lucasvicente.contabancaria.entites.PixKey;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PixKeyService {
    private final PixKeyRepository pixKeyRepository;
    private final AccountRepository accountRepository;

    public PixKeyService(PixKeyRepository pixKeyRepository, AccountRepository accountRepository) {
        this.pixKeyRepository = pixKeyRepository;
        this.accountRepository = accountRepository;
    }

    public List<PixKeyResponseDTO> findAll() {
        return pixKeyRepository.findAll().stream().map(this::toDTO).toList();
    }

    public PixKeyResponseDTO findById(Long id) {

        PixKey existsPixKey = pixKeyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chave pix não encontrada."));

        return toDTO(existsPixKey);
    }

    public PixKeyResponseDTO insert(PixKeyRequestDTO dto) {

        Account existsAccount = accountRepository.findById(dto.accountId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

        PixKey pixKey = new PixKey();

        pixKey.setKeyValue(dto.keyValue());
        pixKey.setAccount(existsAccount);

        return toDTO(pixKeyRepository.save(pixKey));

    }

    public void delete(Long id) {

        PixKey existsPixKey = pixKeyRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Chave pix não encontrada."));

        pixKeyRepository.deleteById(existsPixKey.getId());
    }

    public PixKeyResponseDTO update(Long id, PixKeyRequestDTO dto) {
        PixKey existsPixKey = pixKeyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chave pix não encontrada."));

        existsPixKey.setKeyValue(dto.keyValue());

        return toDTO(pixKeyRepository.save(existsPixKey));
    }

    public List<PixKeyResponseDTO> findAllByAccountId(Long accountId) {
        return pixKeyRepository.findAllByAccountId(accountId).stream().map(this::toDTO).toList();
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
