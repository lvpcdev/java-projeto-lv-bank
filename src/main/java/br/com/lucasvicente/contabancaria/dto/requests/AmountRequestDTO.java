package br.com.lucasvicente.contabancaria.dto.requests;

import java.math.BigDecimal;

public record AmountRequestDTO(
        BigDecimal amount
) {
}
