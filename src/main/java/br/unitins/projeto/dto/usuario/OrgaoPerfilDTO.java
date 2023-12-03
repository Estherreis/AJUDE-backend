package br.unitins.projeto.dto.usuario;

import jakarta.validation.constraints.NotNull;

public record OrgaoPerfilDTO (
        @NotNull(message = "O perfil deve ser informado.")
        Integer idPerfil,

        @NotNull(message = "O órgão deve ser informado.")
        Long idOrgao
) {
}
