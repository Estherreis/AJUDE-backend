package br.unitins.projeto.service.orgao;

import java.util.List;

import br.unitins.projeto.dto.orgao.OrgaoDTO;
import br.unitins.projeto.dto.orgao.OrgaoResponseDTO;

public interface OrgaoService {

    List<OrgaoResponseDTO> getAll();

    OrgaoResponseDTO findById(Long id);

    OrgaoResponseDTO create(OrgaoDTO productDTO);

    OrgaoResponseDTO update(Long id, OrgaoDTO productDTO);

    OrgaoResponseDTO alterarSituacao(Long id, Boolean situacao);

    void delete(Long id);

    Long count();

}
