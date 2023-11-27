package br.unitins.projeto.dto.usuario;

import br.unitins.projeto.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public record UsuarioResponseDTO(
        Long id,
        String cpf,
        String login,
        Integer nivelSigilo,
        List<OrgaoPerfilResponseDTO> orgaoPerfil
) {
    public UsuarioResponseDTO(Usuario entity) {
        this(entity.getId(), entity.getCpf(), entity.getLogin(), entity.getNivelSigilo(),
                entity.getOrgaoPerfil().stream().map(OrgaoPerfilResponseDTO::new).collect(Collectors.toList())
        );
    }
}
