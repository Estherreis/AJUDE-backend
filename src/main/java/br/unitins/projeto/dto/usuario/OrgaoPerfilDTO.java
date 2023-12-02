package br.unitins.projeto.dto.usuario;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record OrgaoPerfilDTO (
        @NotNull(message = "O perfil deve ser informado.")
        Set<Integer> idPerfil,

        @NotNull(message = "O órgão deve ser informado.")
        Long idOrgao
) {
}
