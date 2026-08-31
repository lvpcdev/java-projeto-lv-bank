package br.com.lucasvicente.contabancaria.dto.requests;

import java.math.BigDecimal;

public record AccountRequestDTO(
        Long personId,
        String password,
        BigDecimal balance,
        Integer accountNumber,
        String agency
) {
}
