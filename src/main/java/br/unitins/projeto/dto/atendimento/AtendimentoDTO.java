package br.unitins.projeto.dto.atendimento;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record AtendimentoDTO(

        @NotNull(message = "O beneficiário deve ser informado.") Long idBeneficiario,

        @NotNull(message = "A situação deve ser informada.")
        Integer idSituacao,

        @NotEmpty(message = "O tipo do benefício deve ser informado.")
        @Size(max = 100, message = "O campo tipo de benefício deve possuir no máximo 100 caracteres.")
        String tipoBeneficio,

        @Size(max = 900, message = "O campo descrição deve possuir no máximo 900 caracteres.")
        String descricao

) {
}
