package br.com.lucasvicente.contabancaria.controller;

import br.com.lucasvicente.contabancaria.dto.AccountDTO.AccountResponseDTO;
import br.com.lucasvicente.contabancaria.dto.AccountDTO.AccountRequestDTO;
import br.com.lucasvicente.contabancaria.dto.AmountDTO.AmountRequestDTO;
import br.com.lucasvicente.contabancaria.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountResponseDTO> findAll(){
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public AccountResponseDTO findById(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @PostMapping
    public AccountResponseDTO insert (@Valid @RequestBody AccountRequestDTO dto) {
        return accountService.insert(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deposit")
    public void deposit (@PathVariable Long id, @RequestBody AmountRequestDTO dto) {
        accountService.deposit(id, dto.amount());
    }

    @PatchMapping("/{id}/withdraw")
    public void withdraw(@PathVariable Long id, @RequestBody AmountRequestDTO dto) {
        accountService.withdraw(id, dto.amount());
    }

    @PatchMapping("/sendpix/{issuerId}/{receiverPixKey}")
    public void sendPix(@PathVariable String receiverPixKey,@PathVariable Long issuerId, @RequestBody AmountRequestDTO dto) {
        accountService.sendPix(receiverPixKey, issuerId, dto.amount());
    }
}
