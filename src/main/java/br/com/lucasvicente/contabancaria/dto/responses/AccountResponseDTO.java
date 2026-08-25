package br.com.lucasvicente.contabancaria.dto.responses;

import br.com.lucasvicente.contabancaria.dto.PersonResumeDTO;

import java.math.BigDecimal;
import java.util.List;

public record AccountResponseDTO (
        Long id,
        PersonResumeDTO person,
        BigDecimal balance,
        Integer accountNumber,
        String agency,
        List<String> pixKeys
){
}
