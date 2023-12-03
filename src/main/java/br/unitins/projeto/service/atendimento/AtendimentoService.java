package br.unitins.projeto.service.atendimento;

import br.unitins.projeto.dto.atendimento.AtendimentoDTO;
import br.unitins.projeto.dto.atendimento.AtendimentoResponseDTO;
import br.unitins.projeto.dto.atendimento.AtendimentoUpdateDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface AtendimentoService {

    List<AtendimentoResponseDTO> getAll();

    AtendimentoResponseDTO findById(Long id);

    List<AtendimentoResponseDTO> findByBeneficiario(Long idBeneficiario, int page, int pageSize);

    AtendimentoResponseDTO create(@Valid AtendimentoDTO productDTO);

    AtendimentoResponseDTO update(Long id, @Valid AtendimentoUpdateDTO productDTO);

    AtendimentoResponseDTO finalizarAtendimento(Long id);

    void delete(Long id);

    Long countByBeneficiario(Long idBeneficiario);

}
