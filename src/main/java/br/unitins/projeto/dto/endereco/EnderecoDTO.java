package br.unitins.projeto.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record EnderecoDTO (


    @NotBlank(message = "O campo idMunicipio deve ser informado.")
    Long idMunicipio,

    @NotBlank(message = "O campo bairro deve ser informado.")
    @Size(max = 40, message = "O sigla deve posssuir 40 caracteres.")
    String bairro,

    @NotBlank(message = "O campo logradouro deve ser informado.")
    @Size(max = 40, message = "O sigla deve posssuir 40 caracteres.")
    String logradouro,

    @NotEmpty(message = "O campo numero deve ser informado.")
    Long numero,
    
    @Size(max = 40, message = "O sigla deve posssuir 40 caracteres.")
    String complemento,

    @NotBlank(message = "O cep deve ser preenchido.")
    String cep

) {

}