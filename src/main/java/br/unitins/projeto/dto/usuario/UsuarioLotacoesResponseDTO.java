package br.unitins.projeto.dto.usuario;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.projeto.model.OrgaoPerfil;
import br.unitins.projeto.model.Usuario;

public record UsuarioLotacoesResponseDTO(
        Long id,
        String nome,
        String cpf,
        String login,
        Integer nivelSigilo,
        List<OrgaoPerfilResponseDTO> lotacoes,
        Boolean ativo) {

    public UsuarioLotacoesResponseDTO(Usuario entity, List<OrgaoPerfil> lotacoes) {
        this(entity.getId(), entity.getNome(), entity.getCpf(), entity.getLogin(),
                entity.getNivelSigilo(), gerarOrgaoPerfilResponseDTO(lotacoes), entity.getAtivo());
    }

    public static List<OrgaoPerfilResponseDTO> gerarOrgaoPerfilResponseDTO(List<OrgaoPerfil> list) {
        if (list != null)
            return list.stream().map(OrgaoPerfilResponseDTO::new).collect(Collectors.toList());
        return null;
    }
}
