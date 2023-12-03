package br.unitins.projeto.service.movimentacao;

import java.util.List;

import br.unitins.projeto.dto.movimentacao.MovimentacaoDTO;
import br.unitins.projeto.dto.movimentacao.MovimentacaoResponseDTO;
import jakarta.validation.Valid;

public interface MovimentacaoService {
    
    MovimentacaoResponseDTO lancarMovimentacao(@Valid MovimentacaoDTO movimentacaoDTO);

    List<MovimentacaoResponseDTO> listarPorAtendimento(Long idAtendimento);
}
