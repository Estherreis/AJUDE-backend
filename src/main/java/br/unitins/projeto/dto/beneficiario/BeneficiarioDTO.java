package br.unitins.projeto.dto.beneficiario;

import java.time.LocalDate;

import br.unitins.projeto.dto.endereco.EnderecoDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BeneficiarioDTO(

    @NotBlank(message = "O campo nome deve ser informado.")
    @Size(max = 40, message = "O sigla deve posssuir 40 caracteres.")
    String nome,

    @NotBlank(message = "O campo CPF deve ser informado.")
    @Size(max = 11, message = "O campo CPF deve possuir no máximo 11 caracteres.")
    String cpf,

    @NotBlank(message = "O campo RG deve ser informado.")
    @Size(max = 12, message = "O campo RG deve possuir no máximo 12 caracteres.")
    String rg,

    @NotBlank(message = "O campo CPF Pai/Mãe deve ser informado.")
    @Size(max = 11, message = "O campo CPF do Pai/Mãe deve possuir no máximo 11 caracteres.")
    String cpfPais,

    @NotBlank(message = "O campo NIS deve ser informado.")
    @Size(max = 15, message = "O campo NIS deve possuir no máximo 15 caracteres.")
    String nis,

    LocalDate nascimento,

    EnderecoDTO enderecoDTO

) {}
