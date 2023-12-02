package br.unitins.projeto.dto.atendimento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AtendimentoDTO (

        @NotNull(message = "O beneficiário deve ser informado.") Long idBeneficiario,

        @Size(max = 100, message = "O campo descrição deve possuir no máximo 100 caracteres.")
        String descricao

) {
}
