package br.com.lucasvicente.contabancaria.dto;

import java.math.BigDecimal;
import java.util.List;
import br.com.lucasvicente.contabancaria.dto.PersonDTO.PersonResumeDTO;
import br.com.lucasvicente.contabancaria.dto.PixKeyDTO.PixKeyResumeDTO;

public final class AccountDTO {
    public record AccountRequestDTO(
            Long personId,
            String password
    ) {}
    public record AccountResponseDTO (
            Long id,
            PersonResumeDTO person,
            BigDecimal balance,
            Integer accountNumber,
            String agency,
            List<PixKeyResumeDTO> pixKeys
    ){}
    public record AccountResumeDTO(
            Long id,
            PersonResumeDTO person
    ) {
    }
}
