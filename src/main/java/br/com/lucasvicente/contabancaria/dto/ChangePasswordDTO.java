package br.com.lucasvicente.contabancaria.dto;

public final class ChangePasswordDTO {
    public record ChangePasswordRequestDTO(
            String newPassword
    ){}
}
