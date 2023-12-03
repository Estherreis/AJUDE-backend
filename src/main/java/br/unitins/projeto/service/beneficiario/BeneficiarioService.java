package br.unitins.projeto.service.beneficiario;

import java.util.List;

import br.unitins.projeto.dto.beneficiario.BeneficiarioDTO;
import br.unitins.projeto.dto.beneficiario.BeneficiarioResponseDTO;
import jakarta.validation.Valid;

public interface BeneficiarioService {
    

    List<BeneficiarioResponseDTO> getAll();

    BeneficiarioResponseDTO findById(Long id);

    BeneficiarioResponseDTO create(@Valid BeneficiarioDTO productDTO);

    BeneficiarioResponseDTO update(Long id, @Valid BeneficiarioDTO productDTO);

    List<BeneficiarioResponseDTO> findByNome(String nome);

    List<BeneficiarioResponseDTO> findByCpf(String cpf);

    void delete(Long id);

    Long count();
}
