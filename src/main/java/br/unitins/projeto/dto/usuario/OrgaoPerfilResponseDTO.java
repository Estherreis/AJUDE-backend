package br.unitins.projeto.dto.usuario;

import br.unitins.projeto.dto.orgao.OrgaoResponseDTO;
import br.unitins.projeto.model.Orgao;
import br.unitins.projeto.model.OrgaoPerfil;

import java.util.Set;
import java.util.stream.Collectors;

public record OrgaoPerfilResponseDTO (
        Long id,
        String perfil,
        OrgaoResponseDTO orgaoResponseDTO
) {
    public OrgaoPerfilResponseDTO(OrgaoPerfil entity) {
        this(entity.getId(), entity.getPerfil().getLabel(), gerarOrgaoResponseDTO(entity.getOrgao()));
    }

    public static OrgaoResponseDTO gerarOrgaoResponseDTO(Orgao orgao) {
        OrgaoResponseDTO orgaoResponseDTO = new OrgaoResponseDTO(orgao);
        return orgaoResponseDTO;
    }
}
