package br.unitins.projeto.dto.atendimento;

import jakarta.validation.constraints.Size;

public record AtendimentoUpdateDTO(

        @Size(max = 100, message = "O campo tipo de benefício deve possuir no máximo 100 caracteres.")
        String tipoBeneficio,

        @Size(max = 900, message = "O campo descrição deve possuir no máximo 900 caracteres.")
        String descricao

) {
}
