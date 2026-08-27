package br.com.lucasvicente.contabancaria.dto.responses;


public record PersonResponseDTO(
        Long id,
        String fullName,
        String cpf
) {
}
