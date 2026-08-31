package br.com.lucasvicente.contabancaria.dto.responses;

import br.com.lucasvicente.contabancaria.dto.AccountResumeDTO;

public record PixKeyResponseDTO(
        Long id,
        String keyValue,
        AccountResumeDTO account
) {
}
