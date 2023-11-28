package br.unitins.projeto.dto.usuario;

import br.unitins.projeto.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String cpf,
        String login,
        Integer nivelSigilo
        ) {
    public UsuarioResponseDTO(Usuario entity) {
        this(entity.getId(), entity.getCpf(), entity.getLogin(), entity.getNivelSigilo()
        );
    }
}
