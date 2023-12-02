package br.unitins.projeto.dto.atendimento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record AtendimentoDTO (

        @NotNull(message = "O órgão deve ser informado.") Long idOrgao,

        @NotNull(message = "O beneficiário deve ser informado.") Long idBeneficiario,

        @NotNull(message = "O usuário deve ser informado.") Long idUsuarioInclusao,

        @Size(max = 100, message = "O campo descrição deve possuir no máximo 100 caracteres.")
        String descricao

) {
}
