package br.unitins.projeto.dto.movimentacao;

import java.time.LocalDateTime;

import br.unitins.projeto.dto.atendimento.AtendimentoResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.model.Movimentacao;

public record MovimentacaoResponseDTO(

    Long id,
    UsuarioResponseDTO nomeAutor,
    String descricao,
    AtendimentoResponseDTO atendimento,
    LocalDateTime dataInclusao
) {
        public MovimentacaoResponseDTO (Movimentacao entity){
            
            this(entity.getId(), new UsuarioResponseDTO(entity.getUsuarioAutor()), entity.getDescricao(),
            new AtendimentoResponseDTO(entity.getAtendimento()), entity.getDataInclusao());
        }
}
