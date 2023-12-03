package br.unitins.projeto.dto.movimentacao;

import java.time.LocalDate;

public record MovimentacaoDTO(

    Long idAtendimento,

    LocalDate dataInclusao,

    String tituloMovimentacao

) {
    
}
