package br.com.lucasvicente.contabancaria.dto.responses;

import br.com.lucasvicente.contabancaria.dto.BankResumeDTO;
import br.com.lucasvicente.contabancaria.dto.PersonResumeDTO;

import java.math.BigDecimal;
import java.util.List;

public record AccountResponseDTO (
        Long id,
        BankResumeDTO bank,
        PersonResumeDTO person,
        String password,
        BigDecimal balance,
        Integer accountNumber,
        String agency,
        List<String> pixKeys
){
}
