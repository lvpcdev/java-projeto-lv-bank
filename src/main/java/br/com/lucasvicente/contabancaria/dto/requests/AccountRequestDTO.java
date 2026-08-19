package br.com.lucasvicente.contabancaria.dto.requests;

import java.math.BigDecimal;
import java.util.List;

public record AccountRequestDTO(
        Long bankId,
        Long personId,
        String password,
        BigDecimal balance,
        Integer accountNumber,
        String agency,
        List<String> pixKeys
) {
}
