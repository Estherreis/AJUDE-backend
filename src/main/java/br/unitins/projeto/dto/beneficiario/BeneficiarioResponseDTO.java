package br.unitins.projeto.dto.beneficiario;

import java.time.LocalDate;

import br.unitins.projeto.dto.endereco.EnderecoResponseDTO;
import br.unitins.projeto.model.Beneficiario;

public record BeneficiarioResponseDTO(

    Long id,
    String nome,
    String cpf,
    String rg,
    LocalDate dataNascimento,
    EnderecoResponseDTO enderecoResponseDTO,
    String nis,
    String cpfPai
) {
    public BeneficiarioResponseDTO (Beneficiario entity){
        this(entity.getId(), entity.getNome(), entity.getCpf(), entity.getRg(), entity.getDataNascimento(), 
        new EnderecoResponseDTO(entity.getEndereco()), entity.getNis(), entity.getCpfPai());
    }
}