package br.unitins.projeto.service.atendimento;

import br.unitins.projeto.dto.atendimento.AtendimentoDTO;
import br.unitins.projeto.dto.atendimento.AtendimentoResponseDTO;
import br.unitins.projeto.dto.atendimento.AtendimentoUpdateDTO;

import java.util.List;

public interface AtendimentoService {

    List<AtendimentoResponseDTO> getAll();

    AtendimentoResponseDTO findById(Long id);

    List<AtendimentoResponseDTO> findByBeneficiario(Long idBeneficiario);

    AtendimentoResponseDTO create(AtendimentoDTO productDTO);

    AtendimentoResponseDTO update(Long id, AtendimentoUpdateDTO productDTO);

    AtendimentoResponseDTO finalizarAtendimento(Long id);

    void delete(Long id);

    Long count();

}
