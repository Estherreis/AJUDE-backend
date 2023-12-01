package br.unitins.projeto.service.orgao;

import br.unitins.projeto.dto.orgao.OrgaoDTO;
import br.unitins.projeto.dto.orgao.OrgaoResponseDTO;

import java.util.List;

public interface OrgaoService {

    List<OrgaoResponseDTO> getAll();

    List<OrgaoResponseDTO> getAll(int page, int pageSize);

    OrgaoResponseDTO findById(Long id);

    OrgaoResponseDTO create(OrgaoDTO productDTO);

    OrgaoResponseDTO update(Long id, OrgaoDTO productDTO);

    OrgaoResponseDTO alterarSituacao(Long id, Boolean situacao);

    void delete(Long id);

    Long count();

}
