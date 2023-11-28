package br.unitins.projeto.service.movimentacao;

import java.util.List;

import br.unitins.projeto.dto.movimentacao.MovimentacaoDTO;
import br.unitins.projeto.dto.movimentacao.MovimentacaoResponseDTO;

public interface MovimentacaoService {
    
    MovimentacaoResponseDTO lancarMovimentacao(MovimentacaoDTO movimentacaoDTO);

    List<MovimentacaoResponseDTO> listarPorAtendimento(Long idAtendimento);
}
