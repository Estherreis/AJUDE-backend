package br.unitins.projeto.service.beneficiario;

import java.util.List;

import br.unitins.projeto.dto.beneficiario.BeneficiarioDTO;
import br.unitins.projeto.dto.beneficiario.BeneficiarioResponseDTO;

public interface BeneficiarioService {
    

    List<BeneficiarioResponseDTO> getAll();

    List<BeneficiarioResponseDTO> findByNomeOuCpf(String nomeOuCpf, int page, int pageSize);

    Long countByNomeOuCpf(String nomeOuCpf);

    BeneficiarioResponseDTO findById(Long id);

    BeneficiarioResponseDTO create(BeneficiarioDTO productDTO);

    BeneficiarioResponseDTO update(Long id, BeneficiarioDTO productDTO);

    List<BeneficiarioResponseDTO> findByNome(String nome);

    List<BeneficiarioResponseDTO> findByCpf(String cpf);

    void delete(Long id);

    Long count();
}
