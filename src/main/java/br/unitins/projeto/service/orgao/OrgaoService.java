package br.unitins.projeto.service.orgao;

import br.unitins.projeto.dto.orgao.OrgaoDTO;
import br.unitins.projeto.dto.orgao.OrgaoResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface OrgaoService {

    List<OrgaoResponseDTO> getAll();

    List<OrgaoResponseDTO> getAll(int page, int pageSize);

    OrgaoResponseDTO findById(Long id);

    OrgaoResponseDTO create(@Valid OrgaoDTO productDTO);

    OrgaoResponseDTO update(Long id, @Valid OrgaoDTO productDTO);

    OrgaoResponseDTO alterarSituacao(Long id, Boolean situacao);

    void delete(Long id);

    List<OrgaoResponseDTO> findByNomeOuSigla(String nomeOuSigla, int page, int pageSize);

    Long countByNomeOuSigla(String nomeOuSigla);

    Long count();

}
