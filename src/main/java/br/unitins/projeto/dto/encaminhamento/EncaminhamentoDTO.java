package br.unitins.projeto.dto.encaminhamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EncaminhamentoDTO(
        @NotNull(message = "O órgão de destino deve ser informado.") Long idOrgao,

        @NotNull(message = "O atendimento deve ser informado.") Long idAtendimento,

        @NotBlank(message = "O motivo deve ser informado.")
        @Size(max = 150, message = "O motivo deve possuir no máximo 150 caracteres.")
        String motivo
) {
}
