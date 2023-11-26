package br.unitins.projeto.dto.orgao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrgaoDTO (
        @NotBlank(message = "O campo nome deve ser informado.")
        @Size(max = 60, message = "O campo nome deve possuir no máximo 60 caracteres.")
        String nome,

        @NotBlank(message = "O campo sigla deve ser informado.")
        @Size(max = 15, message = "O campo sigla deve possuir no máximo 60 caracteres.")
        String sigla,

        @NotNull(message = "O municipio deve ser informado.") Long idMunicipio
) {
}
