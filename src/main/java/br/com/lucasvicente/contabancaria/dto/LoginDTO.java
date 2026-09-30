package br.com.lucasvicente.contabancaria.dto;

public final class LoginDTO {
    public record LoginRequestDTO(
            String cpf,
            String password
    ) {}
}
