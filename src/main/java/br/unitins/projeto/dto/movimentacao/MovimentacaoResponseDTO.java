package br.unitins.projeto.dto.movimentacao;

import br.unitins.projeto.dto.atendimento.AtendimentoResponseDTO;
import br.unitins.projeto.dto.usuario.UsuarioResponseDTO;
import br.unitins.projeto.model.Movimentacao;

import java.time.LocalDateTime;

public record MovimentacaoResponseDTO(

        Long id,
        UsuarioResponseDTO usuarioAutor,
        String descricao,
        AtendimentoResponseDTO atendimento,
        LocalDateTime dataInclusao,
        String tituloMovimentacao,
        String nomeDocumento

) {
    public MovimentacaoResponseDTO(Movimentacao entity) {

        this(entity.getId(), new UsuarioResponseDTO(entity.getUsuarioAutor()), entity.getDescricao(),
                new AtendimentoResponseDTO(entity.getAtendimento()), entity.getDataInclusao(),
                entity.getTituloMovimentacao(), entity.getNomeDocumento());
    }
}
