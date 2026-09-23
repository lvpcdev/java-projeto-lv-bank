package br.com.lucasvicente.contabancaria.dto;

public final class PersonDTO {
    public record PersonRequestDTO(
            String fullName,
            String cpf,
            String password
    ) {}
    public record PersonResponseDTO(
            Long id,
            String fullName,
            String cpf
    ) {}
    public record PersonResumeDTO (
            Long id,
            String fullName
    ){}

}
