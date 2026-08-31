package br.com.lucasvicente.contabancaria.dto.requests;

public record PixKeyRequestDTO(
        String keyValue,
        Long accountId
) {
}
