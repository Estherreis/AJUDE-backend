package br.unitins.projeto.dto.atendimento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AtendimentoUpdateDTO(

        @Size(max = 100, message = "O campo descrição deve possuir no máximo 100 caracteres.")
        String descricao

) {
}
