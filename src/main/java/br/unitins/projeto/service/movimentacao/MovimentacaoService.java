package br.unitins.projeto.service.movimentacao;

import br.unitins.projeto.dto.movimentacao.MovimentacaoDTO;
import br.unitins.projeto.dto.movimentacao.MovimentacaoResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface MovimentacaoService {

    List<MovimentacaoResponseDTO> findByAtendimento(Long idAtendimento, int page, int pageSize);

    MovimentacaoResponseDTO lancarMovimentacao(@Valid MovimentacaoDTO movimentacaoDTO);

    MovimentacaoResponseDTO findById(Long id);

    Long countByAtendimento(Long idAtendimento);

}
