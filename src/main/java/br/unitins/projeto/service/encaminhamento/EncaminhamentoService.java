package br.unitins.projeto.service.encaminhamento;

import br.unitins.projeto.dto.encaminhamento.EncaminhamentoDTO;
import br.unitins.projeto.dto.encaminhamento.EncaminhamentoResponseDTO;

import java.util.List;

public interface EncaminhamentoService {

    List<EncaminhamentoResponseDTO> findByAtendimento(Long idAtendimento);

    EncaminhamentoResponseDTO findById(Long id);

    EncaminhamentoResponseDTO create(EncaminhamentoDTO productDTO);

}
