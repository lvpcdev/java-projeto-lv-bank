package br.com.lucasvicente.contabancaria.dto;

import br.com.lucasvicente.contabancaria.dto.AccountDTO.AccountResumeDTO;
public final class PixKeyDTO {
    public record PixKeyRequestDTO(
            String keyValue,
            Long accountId
    ) {}
    public record PixKeyResponseDTO(
            Long id,
            String keyValue,
            AccountResumeDTO account
    ) {}
    public record PixKeyResumeDTO(
            Long id,
            String keyValue
    ){}


}
