package br.unitins.projeto.dto.movimentacao;

import java.time.LocalDate;

import br.unitins.projeto.dto.atendimento.AtendimentoDTO;
import br.unitins.projeto.dto.usuario.UsuarioDTO;
import br.unitins.projeto.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MovimentacaoDTO(

    Long usuarioAutor,

    Long idAtendimento,

    @NotBlank(message = "O campo descrição deve ser informado")
    @Size(max = 300, message = "Máximo de 300 caracteres!")
    String descricao, 

    LocalDate dataInclusao

) {
    
}
