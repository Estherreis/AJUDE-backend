package br.unitins.projeto.dto.movimentacao;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record MovimentacaoDTO(

        Long idAtendimento,

        @NotEmpty(message = "É necessário informar o campo título")
        @Size(max = 100, message = "O campo título deve possuir no máximo 100 caracteres.")
        String tituloMovimentacao,

        @Size(max = 350, message = "O campo descrição deve possuir no máximo 350 caracteres.")
        String descricao

) {

}
