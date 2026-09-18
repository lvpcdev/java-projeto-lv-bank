package br.com.lucasvicente.contabancaria.dto;

import java.math.BigDecimal;

public final class AmountDTO {
    public record AmountRequestDTO(
            BigDecimal amount
    ) {}
}
