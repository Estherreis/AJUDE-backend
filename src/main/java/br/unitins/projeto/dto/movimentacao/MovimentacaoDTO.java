package br.unitins.projeto.dto.movimentacao;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MovimentacaoDTO(

    Long idAtendimento,

    LocalDate dataInclusao,

    String tituloMovimentacao

) {
    
}
